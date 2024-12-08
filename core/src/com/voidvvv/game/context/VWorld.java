package com.voidvvv.game.context;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Pools;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.box2d.testt.CollisionListener;
import com.voidvvv.game.ActGame;
import com.voidvvv.game.asset.AssetConstant;
import com.voidvvv.game.base.VActor;
import com.voidvvv.game.base.VPhysicAttr;
import com.voidvvv.game.base.b2d.UserData;
import com.voidvvv.game.base.shape.VCube;
import com.voidvvv.game.base.wall.Wall;
import com.voidvvv.game.context.input.Pinpoint;
import com.voidvvv.game.context.input.PinpointData;
import com.voidvvv.game.context.map.VMap;
import com.voidvvv.game.manager.behaviors.DamageBehavior;
import com.voidvvv.game.manager.event.VWorldEventManager;
import com.voidvvv.game.screen.test.ui.Box2dUnitConverter;
import com.voidvvv.game.utils.ReflectUtil;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class VWorld {


    public static final VActor.VActorCompare DEFAULT_ACTOR_COMPARE = new VActor.VActorCompare();

    protected List<VActor> initList = new ArrayList<>();
    protected List<VActor> bin = new ArrayList<>();

    boolean initialized = false;

    protected Stage stage;

    private final List<VActor> actorList = new ArrayList<>();

    private final List<VActor> updateList = new ArrayList<>();

    private final List<VActor> renderList = new ArrayList<>();

    private World box2dWorld;

    protected Rectangle boundingBox = new Rectangle();

    protected VMap map;

    public Vector2 currentPointerPose = new Vector2();

    protected PinpointData pinpointData;

    protected BattleContext battleContext;

    protected VWorldEventManager vWorldEventManager;

    protected VActor protagonist;
    // private
    private TiledMap renderMap;

    private OrthogonalTiledMapRenderer tiledMapRenderer;


    public VMap getMap() {
        return map;
    }

    public Rectangle getBoundingBox() {
        return boundingBox;
    }

    public VWorld() {
        map = new VMap();
        pinpointData = new PinpointData();
    }

    public List<VActor> allActors() {

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
        t.getFixture().getBody().setActive(true);
        t.setWorld(this);

        return t;
    }

    public PinpointData getPinpointData() {
        return pinpointData;
    }

    public void setPinpointData(PinpointData pinpointData) {
        this.pinpointData = pinpointData;
    }

    protected WorldHelper helper;

    public void init(WorldHelper helper) {
        this.helper = helper;
        preInit();
        vWorldEventManager = ActGame.gameInstance().getvWorldEventManager();
        // load map
        loadMap();
        initWorld();
        initStage();

        // init graph
        initGraph();

        initialized = true;
        // after init
        postInit();
        this.helper = null;
    }

    protected void preInit() {
    }

    protected void loadMap() {
        if (helper.map != null) {
            renderMap = helper.map;
        } else {
            // default
            renderMap = ActGame.gameInstance().getAssetManager().get(AssetConstant.MAP_TEST_01);
        }
        TiledMapTileLayer mainLayer = (TiledMapTileLayer) renderMap.getLayers().get("main");
        boundingBox.set(mainLayer.getOffsetX(), -mainLayer.getOffsetY(), mainLayer.getWidth() * mainLayer.getTileWidth(), mainLayer.getHeight() * mainLayer.getTileHeight());
    }

    protected void postInit() {
        // initial map render
        tiledMapRenderer = new OrthogonalTiledMapRenderer(renderMap, ActGame.gameInstance().getDrawManager().getSpriteBatch());
        // build four wall to prevent actor out of range
        final Rectangle bb = boundingBox;
        // horizon wall
        float horizon_breadth = 50f;
        float verticalHeight = 50f;
        spawnVActorObstacle(Wall.class, boundingBox.x - horizon_breadth, bb.y + bb.height / 2, horizon_breadth, bb.height / 2); // left
        spawnVActorObstacle(Wall.class, boundingBox.x + horizon_breadth + boundingBox.width, bb.y + bb.height / 2, horizon_breadth, bb.height / 2); // right
        // vertical wall
        spawnVActorObstacle(Wall.class, boundingBox.x + bb.width / 2, bb.y - verticalHeight, bb.width, verticalHeight); // up
        spawnVActorObstacle(Wall.class, boundingBox.x + bb.width / 2, bb.y + bb.height + verticalHeight, bb.width, verticalHeight); // down

        if (helper.battleContext != null)
            this.battleContext = helper.battleContext;
        else
            this.battleContext = new BaseBattleContext();


        // fill stage
        fillStage();
        if (helper.inputListener != null) {
            addListener(helper.inputListener);
        }


    }

    protected void initGraph() {
        map.init(this);
    }


    protected void initStage() {
        Stage stage = helper.stage;
        if (stage == null) {
            OrthographicCamera mainCamera = ActGame.gameInstance().getCameraManager().getMainCamera();
            StretchViewport fillViewport = new StretchViewport(640, 480, mainCamera);
            stage = new Stage(fillViewport
                    , ActGame.gameInstance().getDrawManager().getSpriteBatch());

        }
        if (this.stage != stage) {
            ActGame.gameInstance().removeInputProcessor(this.stage);
            this.stage = stage;
        }
    }

    private void fillStage() {
        // background
        // point
        stage.addActor(new Pinpoint());
    }

    protected void initWorld() {
        box2dWorld = new World(new Vector2(0, 0), true);
        if (helper.collisionListener != null) {
            box2dWorld.setContactListener(helper.collisionListener);
        } else
            box2dWorld.setContactListener(new CollisionListener());
    }

    public void addListener(EventListener listener) {
        this.stage.addListener(listener);
    }


    public void update(float delta) {
        // add
        addInit();
        mainThread(delta);
        clean();
    }

    class VActorCompare implements Comparator<Actor> {

        @Override
        public int compare(Actor o1, Actor o2) {
            VActor v1 = ReflectUtil.cast(o1, VActor.class);
            VActor v2 = ReflectUtil.cast(o2, VActor.class);
            float a1 = v1 == null ? o1.getY() : v1.position.y;
            float a2 = v2 == null ? o2.getY() : v2.position.y;

            return (int) (a2 - a1);
        }
    }

    VActorCompare compare = new VActorCompare();

    protected void mainThread(float delta) {
        vWorldEventManager.update(delta);
        box2dWorld.step(delta, 20
                , 20);
        stage.act(delta);
        stage.getRoot().getChildren().sort(compare);
    }

    private void addInit() {
        for (VActor actor : initList) {
            registerActor(actor);
        }
        initList.clear();
    }

    private void clean() {
        // clean
        if (!bin.isEmpty()) {
            for (VActor t : bin) {
                stage.getRoot().removeActor(t);
                actorList.remove(t);
                updateList.remove(t);
                renderList.remove(t);
                Pools.free(t);
            }
            bin.clear();
        }
    }

    public static final float DEFAULT_UNIT = 16;

    public float unit() {
        return DEFAULT_UNIT;
    }


    public <T extends VActor> T spawnVActorObstacle(Class<T> clazz, float initX, float initY, float hx, float hy) {
        return spawnVActorObstacle(clazz, BodyDef.BodyType.StaticBody, initX, initY, hx, hy);
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
            Fixture roleFixture = createObstacle(BodyDef.BodyType.StaticBody, Box2dUnitConverter.worldToBox2d(initX), Box2dUnitConverter.worldToBox2d(initY),
                    Box2dUnitConverter.worldToBox2d(hx), Box2dUnitConverter.worldToBox2d(hy));
            // fill map graph
            VMap currentMap = getMap();
//            currentMap.
            currentMap.addObstacle(Box2dUnitConverter.worldToBox2d(initX - hx), Box2dUnitConverter.worldToBox2d(initY - hy), Box2dUnitConverter.worldToBox2d(hx * 2), Box2dUnitConverter.worldToBox2d(hy * 2));
            t.setFixture(roleFixture);
            ((UserData) roleFixture.getUserData()).setActor(t);

            t.init();
            registerActor(t);
            return t;
        } catch (Throwable e) {

        }
        return null;
    }


    public <T extends VActor> T spawnVActor(Class<T> clazz, VActorSpawnHelper helper) {
        if (!initialized) {
            return null;
        }
        try {
            T t = Pools.obtain(clazz);
            t.setWorld(this);
            t.setVisible(true);
            VPhysicAttr physicAttr = new VPhysicAttr();
            physicAttr.box2dHx = helper.hx;
            physicAttr.box2dHy = helper.hy;
            VCube cube = new VCube();
            cube.xLength = physicAttr.box2dHx * 2f;
            cube.yLength = physicAttr.box2dHy * 2f;
            if (helper.hz > 0f) {
                cube.zLength = helper.hz;
            } else {
                cube.zLength = this.unit();
            }
            physicAttr.setBaseShape(cube);

            t.setPhysicAttr(physicAttr);
            Fixture roleFixture = createFixture(helper);
            if (helper.occupy) {
                // fill map graph
                VMap currentMap = getMap();
//            currentMap.
                currentMap.addObstacle(helper.initX - helper.hx, helper.initY - helper.hy, helper.hx * 2, helper.hy * 2);
            }
            t.setFixture(roleFixture);
            if (helper.userData == null) {
                // default user data
                ((UserData) roleFixture.getUserData()).setActor(t);
            }
            t.init();
            initList.add(t);

            return t;
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return null;
    }

    public void destroyActor(VActor t) {
        t.setVisible(false);
        t.getBody().setActive(false);
        t.setvActive(false);
        bin.add(t);
    }


    public Fixture createObstacle(BodyDef.BodyType bodyType, float initX, float initY, float hx, float hy) {
        return createFixture(bodyType, initX, initY, hx, hy, WorldContext.OBSTACLE, WorldContext.ROLE);
    }

    public Fixture createFixture(BodyDef.BodyType bodyType, float initX, float initY, float hx, float hy, short category, short mask) {
        World box2dWorld = this.getBox2dWorld();
        BodyDef bd = new BodyDef();
        bd.type = bodyType;
        bd.position.set(initX, initY);
        Body body = box2dWorld.createBody(bd);
        body.setFixedRotation(true);

        FixtureDef fd = new FixtureDef();
        PolygonShape polygonShape = new PolygonShape();
        polygonShape.setAsBox(hx, hy);
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

    public Fixture createFixture(VActorSpawnHelper helper) {
        World box2dWorld = this.getBox2dWorld();
        BodyDef bd = new BodyDef();
        bd.type = helper.bodyType;
        bd.position.set(Box2dUnitConverter.worldToBox2d(helper.initX), Box2dUnitConverter.worldToBox2d(helper.initY));
        Body body = box2dWorld.createBody(bd);
        body.setFixedRotation(true);

        FixtureDef fd = new FixtureDef();
        PolygonShape polygonShape = new PolygonShape();
        polygonShape.setAsBox(Box2dUnitConverter.worldToBox2d(helper.hx), Box2dUnitConverter.worldToBox2d(helper.hy));
        fd.friction = helper.friction;
        fd.density = helper.density;
        fd.filter.categoryBits = helper.category;
        fd.filter.maskBits = helper.mask;
        fd.shape = polygonShape;
        fd.isSensor = helper.isSensor();
        Fixture fixture = body.createFixture(fd);
        body.setActive(false);
        if (helper.userData == null) {
            UserData userData = new UserData();
            userData.setSubShifting(-helper.hy);
            userData.setCategory(helper.category);
            fixture.setUserData(userData);
        } else {
            fixture.setUserData(helper.userData);
        }
        polygonShape.dispose();
        return fixture;
    }

    public Stage getStage() {
        return stage;
    }

    public void draw() {
        getStage().getViewport().apply();
        ActGame.gameInstance().getCameraManager().getMainCamera().update();
        tiledMapRenderer.setView(ActGame.gameInstance().getCameraManager().getMainCamera());
        tiledMapRenderer.render();
        getStage().draw();

    }


    public BattleContext getBattleContext() {
        return battleContext;
    }

    public void setBattleContext(BattleContext battleContext) {
        this.battleContext = battleContext;
    }

    public void destroy() {
        // destroy this world
    }

    public VActor getProtagonist() {
        return protagonist;
    }

    public void setProtagonist(VActor protagonist) {
        this.protagonist = protagonist;
    }

    public void postBehavior(DamageBehavior damageBehavior) {

    }
}
