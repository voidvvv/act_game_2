package com.voidvvv.game.base.test;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pools;
import com.voidvvv.game.ActGame;
import com.voidvvv.game.base.VCharacter;
import com.voidvvv.game.base.shape.VCube;

public class Bob extends VCharacter {

    @Override
    public void act(float delta) {
        super.act(delta);

    }

    @Override
    public void init() {
        super.init();

        // physics
        VCube cube = new VCube();
        cube.xLength = this.physicAttr.box2dHx * 2f;
        cube.yLength = this.physicAttr.box2dHy * 2f;
        cube.zLength = 10f;
        physicAttr.setBaseShape(cube);
        this.setPhysicAttr(physicAttr);

        this.battleAttr.hp = 100;

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
//        batch.flush();
        BitmapFont baseFont = ActGame.gameInstance().getFontManager().getBaseFont();
        baseFont.setColor(Color.RED);
        ActGame.gameInstance().getDrawManager().enableBlend();
        GlyphLayout layout = Pools.obtain(GlyphLayout.class);
        layout.setText(baseFont, (int)this.getHp()+"");
        float height = layout.height;
        float width = layout.width;
        width/=2;
        baseFont.draw(batch, layout, this.getX() - width, this.getY() + height);
        batch.flush();
        Pools.free(layout);

        ActGame.gameInstance().getDrawManager().disableBlend();
    }
}
