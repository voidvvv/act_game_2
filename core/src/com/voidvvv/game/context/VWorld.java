package com.voidvvv.game.context;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.collision.BoundingBox;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.voidvvv.game.ActGame;
import com.voidvvv.game.base.VActor;
import com.voidvvv.game.base.VPhysicAttr;
import com.voidvvv.game.base.b2d.UserData;
import com.voidvvv.game.base.test.VObstacle;
import com.voidvvv.game.context.map.VMap;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class VWorld {


    public static final VActor.VActorCompare DEFAULT_ACTOR_COMPARE = new VActor.VActorCompare();

    boolean initialized = false;

    private Stage stage;

    private final List<VActor> actorList = new ArrayList<>();

    private final List<VActor> updateList = new ArrayList<>();

    private final List<VActor> renderList = new ArrayList<>();

    private World box2dWorld;

    protected Rectangle boundingBox = new Rectangle();

    protected VMap map;

    public Rectangle getBoundingBox() {
        return boundingBox;
    }

    public VWorld() {
        map = new VMap();
    }

    public List<VActor> allActors () {

        return actorList;
    }

    public World getBox2dWorld() {
        return box2dWorld;
    }

    public <T extends VActor> T registerActor(T t) {
        stage.addActor(t);
        actorList.add(t);
        updateList.add(t);
        renderList.add(t);
        t.setWorld(this);

        UserData data = (UserData )t.getFixture().getUserData();
        short category = data.getCategory();

        return t;
    }

    public void init () {
        initWorld();
        initStage();

        // init graph
        initGraph();

        initialized = true;
    }

    private void initGraph() {
        map.init(this);
    }

    private void initObstacle() {
        spawnVActor(VObstacle.class,WorldContext.defaultBodyType,60,100,20,30);
    }

    private void initStage() {
        if (stage != null) {
            ActGame.gameInstance().removeInputProcessor(stage);
        }
        stage = new Stage(new ScreenViewport(ActGame.gameInstance().getCameraManager().getMainCamera())
                , ActGame.gameInstance().getDrawManager().getSpriteBatch());
        ActGame.gameInstance().addInputProcessor(stage);
    }

    private void initWorld() {
        box2dWorld = new World(new Vector2(0,0),false);
        boundingBox.set(-100,-100,200,200);
    }

    public void addListener(EventListener listener) {
        this.stage.addListener(listener);
    }

    public void update (float delta) {
// physic

        box2dWorld.step(delta,10,10);
        stage.act(delta);
        actorList.sort(DEFAULT_ACTOR_COMPARE);

    }

    public static final float DEFAULT_UNIT = 5f;
    public float unit() {
        return DEFAULT_UNIT;
    }

    public <T extends VActor> T spawnVActor(Class<T> clazz, float initX, float initY, float hx, float hy) {
        return spawnVActor(clazz, WorldContext.defaultBodyType, initX, initY, hx, hy);
    }

    public <T extends VActor> T spawnVActorObstacle(Class<T> clazz, float initX, float initY, float hx, float hy) {
        return spawnVActorObstacle(clazz, WorldContext.defaultBodyType, initX, initY, hx, hy);
    }


        @SuppressWarnings("CheckResult")
    public <T extends VActor> T spawnVActor(Class<T> clazz, BodyDef.BodyType bodyType, float initX, float initY, float hx, float hy) {
        if (!initialized) {
            return null;
        }
        try {
            Constructor<T> constructor = clazz.getConstructor();
            T t = constructor.newInstance();
            t.setWorld(this);
            VPhysicAttr physicAttr = new VPhysicAttr();
            physicAttr.box2dHx = hx;
            physicAttr.box2dHy = hy;
            t.setPhysicAttr(physicAttr);
            Fixture roleFixture = createRoleFixture(bodyType, initX, initY, hx, hy);
            t.setFixture(roleFixture);
            t.init();
            registerActor(t);
            return t;
        } catch (Throwable e) {

        }
        return null;
    }

    @SuppressWarnings("CheckResult")
    public <T extends VActor> T spawnVActorObstacle(Class<T> clazz, BodyDef.BodyType bodyType, float initX, float initY, float hx, float hy) {
        if (!initialized) {
            return null;
        }
        try {
            Constructor<T> constructor = clazz.getConstructor();
            T t = constructor.newInstance();
            t.setWorld(this);
            VPhysicAttr physicAttr = new VPhysicAttr();
            physicAttr.box2dHx = hx;
            physicAttr.box2dHy = hy;
            t.setPhysicAttr(physicAttr);
            Fixture roleFixture = createObstacle(BodyDef.BodyType.StaticBody, initX, initY, hx, hy);
            updateMapForFinder(initX,initY,hx,hx);
            t.setFixture(roleFixture);
            t.init();
            registerActor(t);
            return t;
        } catch (Throwable e) {

        }
        return null;
    }

    private void updateMapForFinder(float initX, float initY, float hx, float hx1) {

    }


    public Fixture createRoleFixture(BodyDef.BodyType bodyType, float initX, float initY, float hx, float hy) {
        return createFixture(bodyType,initX,initY,hx,hy,WorldContext.ROLE,WorldContext.OBSTACLE);
    }

    public  Fixture createObstacle(BodyDef.BodyType bodyType, float initX, float initY, float hx, float hy) {
        return createFixture(bodyType,initX,initY,hx,hy,WorldContext.OBSTACLE,WorldContext.ROLE);
    }

    public  Fixture createFixture(BodyDef.BodyType bodyType, float initX, float initY, float hx, float hy, short category, short mask) {
        World box2dWorld = this.getBox2dWorld();
        BodyDef bd = new BodyDef();
        bd.type = bodyType;
        bd.position.set(initX,initY);
        Body body = box2dWorld.createBody(bd);
//        body.setAngularDamping(1f);
        body.setFixedRotation(true);

        FixtureDef fd = new FixtureDef();
        PolygonShape polygonShape = new PolygonShape();
        polygonShape.setAsBox(hx,hy);
        fd.friction = 0;
        fd.density = 0.5f;
        fd.filter.categoryBits = category;
        fd.filter.maskBits = mask;
        fd.shape = polygonShape;
        Fixture fixture = body.createFixture(fd);
        UserData userData = new UserData();
        userData.setSubShifting(-hy);
        userData.setCategory(category);
        fixture.setUserData(userData);
        polygonShape.dispose();

        return fixture;
    }
}
