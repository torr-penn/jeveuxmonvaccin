package com.gtasoft.jeveuxmonvaccin.control;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.gtasoft.jeveuxmonvaccin.JeVeuxMonVaccin;
import com.gtasoft.jeveuxmonvaccin.center.CenterTools;

public class ControlScreen implements Screen, ApplicationListener {


    Stage stage;
    Skin skin;
    Label lblTitle;
    Label lblLastControl;
    Label lblCenterName;
    Label lblResult;
    Label lblStats;
    Label lbl_continue;
    Label lblNextRdv;
    Label lblNoInternet;
    Label lbl_countdown;

    long lastPhoneControl = 0;
    long deltaTime = 21;//in seconds
    float w;
    float h;
    float stateTime;
    boolean loading = false;
    JeVeuxMonVaccin app;
    boolean translated = false;
    boolean forceCheck = true;
    int page = 0;
    TextButton tbPlatformLink;
    CenterTools centerTools;
    private ImageButton btnBackMenu;
    private ImageButton btnNext;
    private OrthographicCamera camera;
    private FitViewport viewport;
    private SpriteBatch sb;
    private int nbcheck;


    public ControlScreen(JeVeuxMonVaccin app) {
        page = 0;
        nbcheck = 1;
        this.app = app;
        loading = false;
        centerTools = app.getCenterTools();

    }

    @Override
    public void dispose() {
        sb.dispose();
        stage.dispose();


    }

    @Override
    public void create() {
        w = 768;
        h = 1280;
        stateTime = 0f;
        sb = new SpriteBatch();

        skin = app.getGraphicTools().getSkin();

        stage = new Stage();
        camera = new OrthographicCamera();
        viewport = new FitViewport(768, 1280, camera);


        int hmiddle = (int) h / 2;


        lblTitle = new Label("Disponibilités", skin);
        lblLastControl = new Label("[mis à jour : - ]", skin);
        lbl_countdown = new Label("23:59:59", skin);
        lbl_continue = new Label("Poursuivre", skin);

        lblStats = new Label(" - vérifications. Rendez-vous possible - fois.", skin);
        lblCenterName = new Label("", skin);

        lblResult = new Label("vérification en cours" + "", skin);
        tbPlatformLink = new TextButton("Prendre\nrendez-vous", skin, "hotlink");
        lblNextRdv = new Label(" ", skin);
        Color myBlue = new Color(0.2f, 0.2f, 0.7f, 1f);

        Label.LabelStyle lblStyleTitle = new Label.LabelStyle();
        lblStyleTitle.fontColor = app.getGraphicTools().getBluetext();
        lblStyleTitle.font = this.skin.getFont("bar-font");
        lblTitle.setStyle(lblStyleTitle);
        lblTitle.setAlignment(Align.center);
        Label.LabelStyle lblStyleError = new Label.LabelStyle();
        lblStyleError.fontColor = Color.ORANGE;
        lblStyleError.font = this.skin.getFont("explications");
        lblNoInternet = new Label("Erreur - problème de connexion internet? ", skin);
        lblNoInternet.setStyle(lblStyleError);
        lblNoInternet.setAlignment(Align.center);
        lblNoInternet.setPosition(w / 2, h - 250, Align.center);


        Label.LabelStyle lblStyleContinue = new Label.LabelStyle();
        lblStyleContinue.fontColor = myBlue;
        lblStyleContinue.font = this.skin.getFont("explications");
        lblLastControl.setStyle(lblStyleContinue);


        Label.LabelStyle lblStyleCountdown = new Label.LabelStyle();
        lblStyleCountdown.fontColor = app.getGraphicTools().getBluetext();
        lblStyleCountdown.font = this.skin.getFont("list");
        //lblStyleCountdown.font.getData().setScale(1.5f);
        lbl_countdown.setStyle(lblStyleCountdown);
        lbl_countdown.setAlignment(Align.center);

        Label.LabelStyle lblStyleInfo = new Label.LabelStyle();
        lblStyleInfo.fontColor = app.getGraphicTools().getBluetext();
        lblStyleInfo.font = this.skin.getFont("explications");
        lblResult.setAlignment(Align.center);
        lblResult.setStyle(lblStyleInfo);


        Label.LabelStyle lblStyleIntitule = new Label.LabelStyle();
        // lblStyleIntitule.fontColor = app.getGraphicTools().getBluetext();
        lblStyleIntitule.font = this.skin.getFont("menu");
        lblCenterName.setColor(skin.getColor("orange"));
        lblCenterName.setAlignment(Align.center);
        lblCenterName.setStyle(lblStyleIntitule);

        lblNextRdv.setStyle(lblStyleIntitule);
        lblNextRdv.setColor(skin.getColor("green"));
        lblNextRdv.setAlignment(Align.center);


        Label.LabelStyle lblStyleStats = new Label.LabelStyle();
        lblStyleStats.fontColor = Color.LIGHT_GRAY;
        lblStyleStats.font = this.skin.getFont("babel");
        lblStats.setStyle(lblStyleStats);

        lbl_continue.setStyle(lblStyleTitle);
        lbl_continue.setAlignment(Align.center);


        ImageButton.ImageButtonStyle btnStyle = new ImageButton.ImageButtonStyle();
        btnStyle.up = this.skin.getDrawable("imgBack");
        this.btnBackMenu = new ImageButton(btnStyle);


        ImageButton.ImageButtonStyle btnStyleNext = new ImageButton.ImageButtonStyle();
        btnStyleNext.up = this.skin.getDrawable("imgNext");
        this.btnNext = new ImageButton(btnStyleNext);


        tbPlatformLink.addListener(new ClickListener() {
            @Override
            public void touchUp(InputEvent e, float x, float y, int point, int button) {

                goCenterLink();

                return;
            }

            @Override
            public boolean touchDown(InputEvent e, float x, float y, int point, int button) {

                return true;

            }
        });


        btnBackMenu.setSize(64, 64);

        this.btnBackMenu.addListener(new ClickListener() {
            @Override
            public void touchUp(InputEvent e, float x, float y, int point, int button) {


                return;
            }

            @Override
            public boolean touchDown(InputEvent e, float x, float y, int point, int button) {
                app.setScreen(app.mainMenuScreen);
                return true;

            }
        });

        this.btnNext.addListener(new ClickListener() {
            @Override
            public void touchUp(InputEvent e, float x, float y, int point, int button) {


                return;
            }

            @Override
            public boolean touchDown(InputEvent e, float x, float y, int point, int button) {
                app.setScreen(app.alertScreen);
                return true;

            }
        });
        this.lbl_continue.addListener(new ClickListener() {
            @Override
            public void touchUp(InputEvent e, float x, float y, int point, int button) {


                return;
            }

            @Override
            public boolean touchDown(InputEvent e, float x, float y, int point, int button) {
                app.setScreen(app.alertScreen);
                return true;

            }
        });


        // tbPlatformLink.setSize(390, 96);
        this.tbPlatformLink.addListener(new ClickListener() {
            @Override
            public void touchUp(InputEvent e, float x, float y, int point, int button) {
                return;
            }

            @Override
            public boolean touchDown(InputEvent e, float x, float y, int point, int button) {
                goCenterLink();

                return true;
            }
        });

        lblTitle.setPosition(w / 2 - lblTitle.getWidth() / 2, h - 150, Align.center);
        lblLastControl.setPosition(w - 24 - 64 - lblLastControl.getWidth() / 2, h - 24 - 64 - 40);
        btnBackMenu.setPosition(w - 24 - 64, h - 24 - 64);

        lblCenterName.setPosition(w / 2, h / 2 + 320);
        lblResult.setPosition(w / 2, h / 2 + 100, Align.center);
        lblNextRdv.setPosition(w / 2, h / 2 + 20);
        lbl_countdown.setPosition(w / 2, (int) (h / 3), Align.center);


        tbPlatformLink.setPosition(w / 2 - tbPlatformLink.getWidth() / 2, h / 2 - 130);


        btnNext.setPosition(w / 2 - btnNext.getWidth() / 2, hmiddle - 440);
        lbl_continue.setPosition(w / 2 - lbl_continue.getWidth() / 2, hmiddle - 480);

        lblStats.setPosition(20, 20);


        stage.setViewport(viewport);
        stage.addActor(btnBackMenu);
        stage.addActor(lblLastControl);
        stage.addActor(btnNext);
        stage.addActor(lbl_continue);
        stage.addActor(lbl_countdown);
        stage.addActor(lblCenterName);

        stage.addActor(lblTitle);
        stage.addActor(lblResult);
        stage.addActor(lblNextRdv);

        stage.addActor(lblStats);


    }


    private boolean availableLink() {
        if (app.getOptions() != null) {
            if (app.getOptions().getCenterSelected() != null && app.getOptions().getCenterSelected().getLink() != null) {
                return true;
            } else {
                if (centerTools != null) {
                    centerTools.selectCenter(app.getOptions().getCenterId(), app.getOptions().getVaccineId());
                    if (app.getOptions().getCenterSelected() != null && app.getOptions().getCenterSelected().getLink() != null) {
                        return true;
                    }

                }
                System.out.println("center is null -> issue");
            }
        }
        return false;
    }


    private void goCenterLink() {
        if (availableLink()) {
            Gdx.net.openURI(app.getOptions().getCenterSelected().getLink().trim());
        }
    }

    @Override
    public void render() {
        // TODO Auto-generated method stub

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
        sb.setProjectionMatrix(camera.combined);

        sb.begin();
        sb.draw(app.getGraphicTools().getImgBackground(), 0, 0, w, h, 0, 20, 12, 0);
        sb.end();

        makeACheck();
        verifyCheck();
        app.getGraphicTools().isLoadingTextWithAnimation(loading, stage, (int) (w / 2), (int) (h / 3) - 10, true);

        if (centerTools != null && centerTools.getCenterStatus() == centerTools.ERROR_LOADING) {
            if (!stage.getActors().contains(lblNoInternet, true)) {
                stage.addActor(lblNoInternet);
            }
        }

        stage.act(delta);
        stage.draw();
    }


    public void verifyCheck() {
        if (centerTools != null) {
            if (centerTools.getCheckStatus() != CenterTools.NO_LOAD) {
                lblNextRdv.setText(centerTools.getNextRdv());

                lblResult.setText(centerTools.getCheckMsg());
                lblResult.setPosition(w / 2 - lblResult.getWidth() / 2, h / 2 + 100);
                lblLastControl.setText(centerTools.getTimeMsg());
                int res = centerTools.getLastCheckCode();
                if (res == 1 || res == -5) {
                    if (!stage.getActors().contains(tbPlatformLink, true)) {
                        stage.addActor(tbPlatformLink);
                    }
                } else {
                    if (stage.getActors().contains(tbPlatformLink, true)) {
                        tbPlatformLink.remove();
                    }
                }
                centerTools.setCheckStatus(CenterTools.NO_LOAD);
                lblStats.setText(app.getOptions().getCheckTotal() + " vérifications. Rendez-vous possible " + app.getOptions().getSuccessTotal() + " fois.");
                if (nbcheck % 10 == 1) {
                    app.getOptions().saveOptions();
                    nbcheck = nbcheck + 1;
                }
                loading = false;

            }
        }
        if (loading) {
            if (!"".equals(lbl_countdown.getText())) {
                lbl_countdown.setText("");
            }
        } else {
            lbl_countdown.setText(timeLeft());
        }
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
    public void show() {
        page = 0;
        if (sb == null) {
            create();

        }
        Gdx.input.setInputProcessor(stage);
        if (app.getOptions() != null) {
            if (!"".equals(app.getOptions().getCenterName())) {
                lblCenterName.setText(app.getOptions().getCenterName());
            }
        }
        if (centerTools == null) {
            centerTools = app.getCenterTools();
        }
        if (centerTools != null) {
            if (app.getOptions() != null) {
                if (app.getOptions().getCenterSelected() == null || app.getOptions().getCenterSelected().getLink() == null) {
                    centerTools.loadCenter(app.getOptions().getDepartment());
                }
            }
        } else {
            System.out.println("application incoherence");
        }
        if (!app.getOptions().isControlPageSeen()) {
            app.getOptions().setControlPageSeen(true);
            app.getOptions().saveOptions();
        }

        forceCheck = true;

    }

    public String timeLeft() {
        if (lastPhoneControl == 0) {
            return "__:__:__";
        }

        long now = System.currentTimeMillis();
        long diff = lastPhoneControl + deltaTime * 1000 - now;
        return printDiff(diff);
    }

    public String printDiff(long diff) {
        long secs = diff / 1000;
        // long milisec = ((diff - (Math.round(secs) * 1000)));
        String display = String.format("Recherche dans %02d Min %02d Sec", (secs % 3600) / 60, (secs % 60));
        return display;
    }

    public void makeACheck() {
        if (lastPhoneControl == 0) {
            lastPhoneControl = System.currentTimeMillis();
        }
        if ("".equals(lblCenterName.getText().toString())) {
            if (app.getOptions() != null) {
                if (!"".equals(app.getOptions().getCenterName())) {
                    lblCenterName.setText(app.getOptions().getCenterName());
                }
            }
        }
        long now = System.currentTimeMillis();
        if (forceCheck || now > lastPhoneControl + deltaTime * 1000) {

            loading = true;
            forceCheck = false;
            lastPhoneControl = System.currentTimeMillis();
            fullCheck();


        }
    }

    public void fullCheck() {
        if (centerTools == null) {
            centerTools = app.getCenterTools();
        }
        if (centerTools == null) {

            lblResult.setText(" La vérification par l'application est indisponible actuellement.");
            loading = false;

            return;
        }
        centerTools.controlCenter();
        loading = true;


    }


    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }


}
