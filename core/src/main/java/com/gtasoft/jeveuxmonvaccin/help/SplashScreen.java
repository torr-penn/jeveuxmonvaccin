package com.gtasoft.jeveuxmonvaccin.help;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.gtasoft.jeveuxmonvaccin.JeVeuxMonVaccin;
import com.gtasoft.jeveuxmonvaccin.resource.GraphicTools;
import com.gtasoft.jeveuxmonvaccin.resource.NetworkTools;

import java.util.Timer;
import java.util.TimerTask;

public class SplashScreen implements Screen, ApplicationListener {
    boolean translated = false;
    Texture imgBack;
    float w;
    float h;
    JeVeuxMonVaccin app;
    Texture splashImage;
    Label lbl_internet;
    NetworkTools nt;
    long splashTime = 0;
    boolean go = false;
    Stage stage;
    Skin skin;
    private SpriteBatch batch;
    private OrthographicCamera camera;
    private FitViewport viewport;
    private GraphicTools graphicTools;
    private Timer ressourceLoadTimer;
    private ImageButton btnExit;

    public SplashScreen(JeVeuxMonVaccin app) {
        w = 768;
        h = 1280;

        this.app = app;
        nt = new NetworkTools();
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        viewport = new FitViewport(w, h, camera);
    }

    @Override
    public void dispose() {

        batch.dispose();
        splashImage.dispose();

    }

    @Override
    public void render() {

    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0f, 0f, 0f, 0.22f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        if (!translated) {
            camera.translate(camera.viewportWidth / 2, camera.viewportHeight / 2);
            translated = true;
        }
        camera.update();

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(imgBack, 0, 0, w, h, 0, 20, 12, 0);
        batch.draw(splashImage, w / 2 - 512 / 2, h / 2 - 512 / 2);
        if (System.currentTimeMillis() > splashTime - 1000 && splashTime > 0) {
            graphicTools.isLoadingImg(batch, (int) (w - 58) / 2, (int) 250);
        }
        if (System.currentTimeMillis() > splashTime - 500 && splashTime > 0) {
            graphicTools.isLoadingTextWithAnimation(true, stage, (int) (w) / 2, (int) 200, true);
        }
        batch.end();
        stage.act(delta);
        stage.draw();

        if (go) {
            if (nt.isNetworkConnected()) {
                app.centerScreen.initResources();
                app.setScreen(app.mainMenuScreen);
                return;

            } else {
                if (!stage.getActors().contains(lbl_internet, true)) {
                    stage.addActor(lbl_internet);
                }
                if (!stage.getActors().contains(btnExit, true)) {
                    stage.addActor(btnExit);
                }

                return;

            }
        }
        if (System.currentTimeMillis() > splashTime && splashTime > 0) {
            go = true;
        }

        if (Gdx.input.justTouched()) {
            go = true;
        }

    }

    @Override
    public void show() {
        if (stage == null) {
            create();
        }
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void create() {
        stage = new Stage();

        graphicTools = app.getGraphicTools();

        splashImage = new Texture(Gdx.files.internal("img/splash.png"));

        stage.setViewport(viewport);

        imgBack = new Texture(Gdx.files.internal("img/menu/backgroundlight.png"));
        imgBack.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
        skin = app.getGraphicTools().getSkin();

        Label.LabelStyle lblStyleCountdown = new Label.LabelStyle();
        lblStyleCountdown.fontColor = app.getGraphicTools().getBluetext();
        lblStyleCountdown.font = this.skin.getFont("retron2000");
        //lblStyleCountdown.font.getData().setScale(1.5f);
        lbl_internet = new Label("Lancement de l'application impossible\nInternet non détecté ", skin);
        lbl_internet.setStyle(lblStyleCountdown);
        lbl_internet.setPosition(20, 100);
        ImageButton.ImageButtonStyle ibtnStyle = new ImageButton.ImageButtonStyle();
        ibtnStyle.up = skin.getDrawable("imgExit");
        btnExit = new ImageButton(ibtnStyle);

        btnExit.setSize(32, 32);
        btnExit.setPosition(w - 24 - 32, h - 24 - 32);
        btnExit.addListener(new ClickListener() {
            @Override
            public void touchUp(InputEvent e, float x, float y, int point, int button) {
                app.dispose();
                return;
            }

            @Override
            public boolean touchDown(InputEvent e, float x, float y, int point, int button) {
                return true;

            }
        });
        splashTime = System.currentTimeMillis() + 10000;
        ressourceLoadTimer = new Timer();
        this.ressourceLoadTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                app.resourcesInit();
                ressourceLoadTimer.cancel();
                ressourceLoadTimer.purge();

            }
        }, 300);
    }

}
