package com.gtasoft.jeveuxmonvaccin.menu;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.gtasoft.jeveuxmonvaccin.JeVeuxMonVaccin;
import com.gtasoft.jeveuxmonvaccin.setup.Options;

public class MenuScreen implements Screen, ApplicationListener, InputProcessor {


    Stage stage;
    Label lbl_version;
    Label lbl_vaccine;
    Label lbl_vaccineInfo;
    Label lbl_center;
    Label lbl_centerInfo;
    Label lbl_preparation;
    Label lbl_availability;

    Label lbl_alert;
    Label lbl_alertInfo;
    Label lbl_help;
    Label lblTitle;

    Skin skin;
    int step = 0;
    int w;
    int h;
    float stateTime;
    JeVeuxMonVaccin app;
    boolean translated = false;
    InputMultiplexer multiplexer;
    private ImageButton btnHelp;
    private ImageButton btnExit;
    private ImageButton btnVaccine;
    private ImageButton btnCenter;
    private ImageButton btnPreparation;
    private ImageButton btnAlert;
    private ImageButton btnAvailability;
    private ImageButton btnliste;
    private Texture imgBretagne;
    private OrthographicCamera camera;
    private FitViewport viewport;
    private SpriteBatch sb;
    private ShapeRenderer sr;
    private boolean initialized;

    // constructor to keep a reference to the main App class
    public MenuScreen(JeVeuxMonVaccin app) {
        multiplexer = new InputMultiplexer();
        this.app = app;
        camera = new OrthographicCamera();
        viewport = new FitViewport(768, 1280, camera);
        initialized = false;
    }


    @Override
    public void dispose() {
        sb.dispose();
        stage.dispose();
        multiplexer.clear();

    }

    @Override
    public void create() {
        w = 768;
        h = 1280;
        stateTime = 0f;
        sb = new SpriteBatch();
        sr = new ShapeRenderer();
        skin = app.getGraphicTools().getSkin();
        int wmiddle = (int) w / 2;
        int hmiddle = (int) h / 2;

        imgBretagne = new Texture(Gdx.files.internal("img/menu/bretagne.png"));
        Label.LabelStyle lblStyleVersion = new Label.LabelStyle();
        lblStyleVersion.fontColor = Color.DARK_GRAY;
        lblStyleVersion.font = skin.getFont("typewriter");

        Label.LabelStyle lblStyleInfo = new Label.LabelStyle();
        lblStyleInfo.fontColor = Color.DARK_GRAY;
        lblStyleInfo.font = skin.getFont("smallfont");

        ImageButton.ImageButtonStyle ibtnStyleHelp = new ImageButton.ImageButtonStyle();
        ibtnStyleHelp.up = skin.getDrawable("imgHelp");
        btnHelp = new ImageButton(ibtnStyleHelp);


        ImageButton.ImageButtonStyle ibtnStyle = new ImageButton.ImageButtonStyle();
        ibtnStyle.up = skin.getDrawable("imgExit");
        btnExit = new ImageButton(ibtnStyle);

        ImageButton.ImageButtonStyle ibtnStyleMyself = new ImageButton.ImageButtonStyle();
        ibtnStyleMyself.up = skin.getDrawable("imgMyself");
        btnVaccine = new ImageButton(ibtnStyleMyself);

        ImageButton.ImageButtonStyle ibtnStylePreparation = new ImageButton.ImageButtonStyle();
        ibtnStylePreparation.up = skin.getDrawable("imgRegistration");
        btnPreparation = new ImageButton(ibtnStylePreparation);


        ImageButton.ImageButtonStyle ibtnStyleSelect = new ImageButton.ImageButtonStyle();
        ibtnStyleSelect.up = skin.getDrawable("imgSelectPlace");
        btnCenter = new ImageButton(ibtnStyleSelect);


        ImageButton.ImageButtonStyle ibtnStyleAlert = new ImageButton.ImageButtonStyle();
        ibtnStyleAlert.up = skin.getDrawable("imgAlert");
        btnAlert = new ImageButton(ibtnStyleAlert);

        ImageButton.ImageButtonStyle ibtnStyleAvailability = new ImageButton.ImageButtonStyle();
        ibtnStyleAvailability.up = skin.getDrawable("imgSearch");
        btnAvailability = new ImageButton(ibtnStyleAvailability);

        ImageButton.ImageButtonStyle btnStylelist = new ImageButton.ImageButtonStyle();
        btnStylelist.up = this.skin.getDrawable("list-icon");
        this.btnliste = new ImageButton(btnStylelist);


        Label.LabelStyle lblStyleMenu = new Label.LabelStyle();
        lblStyleMenu.fontColor = Color.WHITE;
        lblStyleMenu.font = skin.getFont("menu");

        lbl_vaccineInfo = new Label("", skin);
        lbl_centerInfo = new Label("", skin);
        lbl_alertInfo = new Label("", skin);
        lbl_vaccineInfo.setStyle(lblStyleInfo);
        lbl_centerInfo.setStyle(lblStyleInfo);
        lbl_alertInfo.setStyle(lblStyleInfo);

        lbl_version = new Label(app.getVersion(), skin);
        lbl_version.setPosition(20, 20);
        lbl_version.setStyle(lblStyleVersion);

        stage = new Stage();
        // --------------------
        //  1- VACCIN
        // --------------------

        btnVaccine.setSize(96, 96);
        btnVaccine.setPosition(w / 4, hmiddle + 300);
        btnVaccine.addListener(new ClickListener() {
            @Override
            public void touchUp(InputEvent e, float x, float y, int point, int button) {
                app.setScreen(app.selectScreen);
                return;
            }

            @Override
            public boolean touchDown(InputEvent e, float x, float y, int point, int button) {
                return true;

            }
        });


        lbl_vaccine = new Label("1 La vaccination", skin);
        lbl_vaccine.setAlignment(Align.left);
        lbl_vaccine.setPosition((w / 3 - lbl_vaccine.getWidth() / 2 + btnVaccine.getWidth() + 10), hmiddle + 300 + btnVaccine.getHeight() / 2);
        lbl_vaccineInfo.setPosition((w / 3 - lbl_vaccine.getWidth() / 2 + btnVaccine.getWidth() + 10), hmiddle + 300 + btnVaccine.getHeight() / 2 - 20);

        lbl_vaccine.setStyle(lblStyleMenu);

        lbl_vaccine.addListener(new ClickListener() {
            @Override
            public void touchUp(InputEvent e, float x, float y, int point, int button) {
                app.setScreen(app.selectScreen);
                return;
            }

            @Override
            public boolean touchDown(InputEvent e, float x, float y, int point, int button) {
                return true;

            }
        });
        // --------------------
        //  2- Center+list
        // --------------------
        btnliste.setSize(96, 96);
        btnliste.setPosition(24, hmiddle + 100);
        this.btnliste.addListener(new ClickListener() {
            @Override
            public void touchUp(InputEvent e, float x, float y, int point, int button) {


                return;
            }

            @Override
            public boolean touchDown(InputEvent e, float x, float y, int point, int button) {

                app.setScreen(app.centerListScreen);
                return true;

            }
        });
        btnCenter.setSize(96, 96);
        btnCenter.setPosition(w / 4, hmiddle + 100);
        btnCenter.addListener(new ClickListener() {
            @Override
            public void touchUp(InputEvent e, float x, float y, int point, int button) {
                app.setScreen(app.centerScreen);
                return;
            }

            @Override
            public boolean touchDown(InputEvent e, float x, float y, int point, int button) {
                return true;

            }
        });


        lbl_center = new Label("2 Centre de vaccination", skin);
        lbl_center.setAlignment(Align.left);
        lbl_center.setPosition((w / 4 + btnCenter.getWidth() + 10), hmiddle + 100 + btnCenter.getHeight() / 2);
        lbl_centerInfo.setPosition((w / 4 + btnCenter.getWidth() + 10), hmiddle + 100 + btnCenter.getHeight() / 2 - 20);
        lbl_center.setStyle(lblStyleMenu);

        lbl_center.addListener(new ClickListener() {
            @Override
            public void touchUp(InputEvent e, float x, float y, int point, int button) {
                app.setScreen(app.centerScreen);
                return;
            }

            @Override
            public boolean touchDown(InputEvent e, float x, float y, int point, int button) {
                return true;

            }
        });


        // --------------------
        //  3- Preparation
        // --------------------

        btnPreparation.setSize(96, 96);
        btnPreparation.setPosition(w / 4, hmiddle - 100);
        btnPreparation.addListener(new ClickListener() {
            @Override
            public void touchUp(InputEvent e, float x, float y, int point, int button) {
                app.setScreen(app.prepareScreen);
                return;
            }

            @Override
            public boolean touchDown(InputEvent e, float x, float y, int point, int button) {
                return true;

            }
        });


        lbl_preparation = new Label("3 Préparation", skin);
        lbl_preparation.setAlignment(Align.left);
        lbl_preparation.setPosition((w / 4 + btnPreparation.getWidth() + 10), hmiddle - 100 + btnPreparation.getHeight() / 2);
        lbl_preparation.setStyle(lblStyleMenu);

        lbl_preparation.addListener(new ClickListener() {
            @Override
            public void touchUp(InputEvent e, float x, float y, int point, int button) {
                app.setScreen(app.prepareScreen);
                return;
            }

            @Override
            public boolean touchDown(InputEvent e, float x, float y, int point, int button) {
                return true;

            }
        });

        // --------------------
        //  4- Availability
        // --------------------

        btnAvailability.setSize(96, 96);
        btnAvailability.setPosition(w / 4, hmiddle - 300);
        btnAvailability.addListener(new ClickListener() {
            @Override
            public void touchUp(InputEvent e, float x, float y, int point, int button) {
                app.setScreen(app.controlScreen);
                return;
            }

            @Override
            public boolean touchDown(InputEvent e, float x, float y, int point, int button) {
                return true;

            }
        });


        lbl_availability = new Label("4 Disponibilités", skin);
        lbl_availability.setAlignment(Align.left);
        lbl_availability.setPosition((w / 4 + btnAvailability.getWidth() + 10), hmiddle - 300 + btnAvailability.getHeight() / 2);
        lbl_availability.setStyle(lblStyleMenu);

        lbl_availability.addListener(new ClickListener() {
            @Override
            public void touchUp(InputEvent e, float x, float y, int point, int button) {
                app.setScreen(app.controlScreen);
                return;
            }

            @Override
            public boolean touchDown(InputEvent e, float x, float y, int point, int button) {
                return true;

            }
        });


        // --------------------
        //  5- Alert
        // --------------------

        btnAlert.setSize(96, 96);
        btnAlert.setPosition(w / 4, hmiddle - 500);
        btnAlert.addListener(new ClickListener() {
            @Override
            public void touchUp(InputEvent e, float x, float y, int point, int button) {
                app.setScreen(app.alertScreen);
                return;
            }

            @Override
            public boolean touchDown(InputEvent e, float x, float y, int point, int button) {
                return true;

            }
        });


        lbl_alert = new Label("5 Alertes", skin);
        lbl_alert.setAlignment(Align.left);
        lbl_alert.setPosition((w / 4 + btnAlert.getWidth() + 10), hmiddle - 500 + btnAlert.getHeight() / 2);
        lbl_alertInfo.setPosition((w / 4 + btnAlert.getWidth() + 10), hmiddle - 500 + btnAlert.getHeight() / 2 - 20);
        lbl_alert.setStyle(lblStyleMenu);
        lbl_alert.addListener(new ClickListener() {
            @Override
            public void touchUp(InputEvent e, float x, float y, int point, int button) {
                app.setScreen(app.alertScreen);
                return;
            }

            @Override
            public boolean touchDown(InputEvent e, float x, float y, int point, int button) {
                return true;

            }
        });


        // --------------------
        //  6- Aide - A propos
        // --------------------

        btnHelp.setSize(48, 48);
        btnHelp.setPosition(w / 7, hmiddle - 630);
        btnHelp.addListener(new ClickListener() {
            @Override
            public void touchUp(InputEvent e, float x, float y, int point, int button) {
                app.setScreen(app.helpScreen);
                return;
            }

            @Override
            public boolean touchDown(InputEvent e, float x, float y, int point, int button) {
                return true;

            }
        });


        lbl_help = new Label("Aide et crédits", skin);
        lbl_help.setAlignment(Align.left);
        lbl_help.setPosition((w / 7 + btnHelp.getWidth() + 10), hmiddle - 630 + btnHelp.getHeight() / 4);
        lbl_help.setStyle(lblStyleVersion);

        lbl_help.addListener(new ClickListener() {
            @Override
            public void touchUp(InputEvent e, float x, float y, int point, int button) {
                app.setScreen(app.helpScreen);
                return;
            }

            @Override
            public boolean touchDown(InputEvent e, float x, float y, int point, int button) {
                return true;

            }
        });


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
        lblTitle = new Label("JE VEUX MON VACCIN", skin);
        Label.LabelStyle lblStyleTitle = new Label.LabelStyle();
        lblStyleTitle.fontColor = Color.WHITE;
        lblStyleTitle.font = this.skin.getFont("bar-font");
        lblTitle.setStyle(lblStyleTitle);
        lblTitle.setAlignment(Align.center);
        lblTitle.setPosition(w / 2, h - 150, Align.center);

        stage.setViewport(viewport);
        stage.addActor(btnExit);

        stage.addActor(btnHelp);
        stage.addActor(lbl_help);
        stage.addActor(lblTitle);

        stage.addActor(btnVaccine);
        stage.addActor(lbl_vaccine);


        stage.addActor(lbl_version);

        multiplexer.addProcessor(stage);
        multiplexer.addProcessor(this); // Your screen
        Gdx.input.setInputProcessor(multiplexer);
//        Gdx.input.setInputProcessor(this.stage);

    }

    public Stage getStage() {
        return stage;
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
        sr.setProjectionMatrix(camera.combined);


        sb.begin();
        sb.draw(app.getGraphicTools().getImgBackground(), 0, 0, w, h, 0, 20, 12, 0);

        sb.draw(imgBretagne, (int) (w - imgBretagne.getWidth()), h - imgBretagne.getHeight() - 185);

        sb.end();
        stage.act(delta);
        stage.draw();
        if (step > 0) {
            strikeThrough(sr);
        }

        if (!initialized) {
            checkOptions();
        }
    }

    private void checkOptions() {
        if (app.centerScreen != null
                && app.getOptions().getDepartment() != Options.UNDEFINED) {
            loadCenterList();
            checkStep();
            adaptMenu();
            initialized = true;
        }
    }

    private void strikeThrough(ShapeRenderer sr) {
        if (step >= 1) {
            sr.setColor(Color.LIGHT_GRAY);
            sr.begin(ShapeRenderer.ShapeType.Filled);

//            lbl_vaccine.setPosition((w / 3 - lbl_vaccine.getWidth() / 2 + btnVaccin.getWidth() + 10), hmiddle + 300 + btnVaccin.getHeight() / 2);

//            int myw = Gdx.graphics.getWidth();
            //          int myh = Gdx.graphics.getHeight();
            int myw = w;
            int myh = h;

            sr.rect(myw / 3 - lbl_vaccine.getWidth() / 2 + btnVaccine.getWidth() + 10, myh / 2 + 300f + btnVaccine.getHeight() / 2 + lbl_vaccine.getHeight() / 2, lbl_vaccine.getWidth() * 2, 3);
            if (step >= 2) {
                sr.rect(myw / 3 - lbl_center.getWidth() / 2 + btnCenter.getWidth() + 60, myh / 2 + 100f + btnCenter.getHeight() / 2 + lbl_center.getHeight() / 2, lbl_center.getWidth() * 2, 3);
            }
            if (step >= 3) {
                sr.rect(myw / 3 - lbl_preparation.getWidth() / 2 + btnPreparation.getWidth() + 10, myh / 2 - 100f + btnPreparation.getHeight() / 2 + lbl_availability.getHeight() / 2, lbl_preparation.getWidth() * 2, 3);
            }
            sr.end();
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
        Gdx.input.setInputProcessor(multiplexer);
        if (sb == null) {
            create();
        }
        checkStep();
        adaptMenu();


    }

    private void centerInfo() {
        if (app.getOptions().getCenterName() != null && !"".equals(app.getOptions().getCenterName())) {
            lbl_centerInfo.setText(app.getOptions().getCenterName());
        }
    }

    private void alertInfo() {
        if (app.getOptions().isAlert()) {
            lbl_alertInfo.setText("Actives");
        } else {
            lbl_alertInfo.setText("Non Actives");
        }
    }

    private void vaccineInfo() {
        if (app.getOptions().getVaccineId() != Options.UNDEFINED) {
            if (app.getOptions().getVaccineId() == Options.ASTRAZENECA) {
                lbl_vaccineInfo.setText("[Astrazeneca]");
            }
            if (app.getOptions().getVaccineId() == Options.PFIZER) {
                lbl_vaccineInfo.setText("[ARN-m]");
            }
            if (app.getOptions().getVaccineId() == Options.JANSSEN) {
                lbl_vaccineInfo.setText("[Janssen]");
            }

        }

    }

    private void adaptMenu() {

        Color myyellow = new Color(1, 0.77f, 0, 1);
        displayElements();
        if (step == 0) {
            lbl_vaccine.setColor(myyellow);
            lbl_vaccineInfo.setText("");
            lbl_centerInfo.setText("");
        } else if (step == 1) {

            lbl_center.setColor(myyellow);
            lbl_vaccine.setColor(Color.LIGHT_GRAY);
            lbl_centerInfo.setText("");
            vaccineInfo();
        } else if (step == 2) {

            lbl_center.setColor(Color.LIGHT_GRAY);
            lbl_vaccine.setColor(Color.LIGHT_GRAY);
            lbl_preparation.setColor(myyellow);
            vaccineInfo();
            centerInfo();

        } else if (step == 3) {
            lbl_center.setColor(Color.LIGHT_GRAY);
            lbl_vaccine.setColor(Color.LIGHT_GRAY);
            lbl_preparation.setColor(Color.LIGHT_GRAY);
            lbl_availability.setColor(myyellow);
            vaccineInfo();
            centerInfo();
        } else if (step == 4) {
            lbl_center.setColor(Color.LIGHT_GRAY);
            lbl_vaccine.setColor(Color.LIGHT_GRAY);
            lbl_preparation.setColor(Color.LIGHT_GRAY);
            lbl_availability.setColor(myyellow);
            lbl_alert.setColor(myyellow);
            vaccineInfo();
            centerInfo();
            alertInfo();
        }


    }

    private void displayElements() {
        if (step == 0) {
            hideit(lbl_alertInfo);
            hideit(lbl_vaccineInfo);
            hideit(lbl_centerInfo);

            showit(lbl_vaccine);
            showit(btnVaccine);

            hideit(btnPreparation);
            hideit(lbl_preparation);

            hideit(btnAvailability);
            hideit(lbl_availability);

            hideit(btnCenter);
            hideit(btnliste);
            hideit(lbl_center);

            hideit(btnAlert);
            hideit(lbl_alert);
        } else if (step == 1) {
            showit(lbl_vaccine);
            showit(btnVaccine);

            hideit(lbl_alertInfo);
            showit(lbl_vaccineInfo);
            showit(lbl_centerInfo);

            showit(btnliste);
            showit(btnCenter);
            showit(lbl_center);
            hideit(btnPreparation);
            hideit(lbl_preparation);

            hideit(btnAvailability);
            hideit(lbl_availability);


            hideit(btnAlert);
            hideit(lbl_alert);
        } else if (step == 2) {
            showit(lbl_vaccine);
            showit(btnVaccine);

            hideit(lbl_alertInfo);
            showit(lbl_vaccineInfo);
            showit(lbl_centerInfo);

            showit(btnliste);
            showit(btnCenter);
            showit(lbl_center);

            showit(btnPreparation);
            showit(lbl_preparation);

            hideit(btnAvailability);
            hideit(lbl_availability);


            hideit(btnAlert);
            hideit(lbl_alert);
        } else if (step == 3) {
            showit(lbl_vaccine);
            showit(btnVaccine);
            hideit(lbl_alertInfo);
            showit(lbl_vaccineInfo);
            showit(lbl_centerInfo);
            showit(btnliste);
            showit(btnCenter);
            showit(lbl_center);

            showit(btnPreparation);
            showit(lbl_preparation);

            showit(btnAvailability);
            showit(lbl_availability);


            hideit(btnAlert);
            hideit(lbl_alert);
        } else if (step == 4) {
            showit(lbl_vaccine);
            showit(btnVaccine);
            showit(lbl_alertInfo);
            showit(lbl_vaccineInfo);
            showit(lbl_centerInfo);
            showit(btnliste);
            showit(btnCenter);
            showit(lbl_center);

            showit(btnPreparation);
            showit(lbl_preparation);

            showit(btnAvailability);
            showit(lbl_availability);


            showit(btnAlert);
            showit(lbl_alert);
        }

    }

    private void hideit(Actor actor) {
        if (actor != null) {
            if (stage.getActors().contains(actor, true)) {
                actor.remove();
            }
        }

    }

    private void showit(Actor actor) {
        if (actor != null) {
            if (!stage.getActors().contains(actor, true)) {
                stage.addActor(actor);
            }
        }

    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);

        app.getOptions().saveOptions();

    }

    @Override
    public boolean keyDown(int keycode) {
        if (Gdx.input.isKeyPressed(Input.Keys.Q)) {
            app.dispose();
            return true;
        } else if (Gdx.input.isKeyPressed(Input.Keys.H)) {
            app.setScreen(app.helpScreen);
            return true;
        }
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        // TODO Auto-generated method stub

        return true;
    }

    @Override
    public boolean keyTyped(char character) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {

        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {

        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }

//    @Override
//    public boolean scrolled(int amount) {
//        // TODO Auto-generated method stub
//        return false;
//    }


    public Skin getSkin() {
        return skin;
    }

    public void setSkin(Skin skin) {
        this.skin = skin;
    }

    public JeVeuxMonVaccin getApp() {
        return app;
    }

    public void loadCenterList() {
        if (app.centerScreen != null && app.getOptions().getDepartment() != Options.UNDEFINED) {
            app.centerScreen.loadCenter(app.getOptions().getDepartment());

            //app.centerScreen.initCenter(app.getOptions().getCenterId(), app.getOptions().getVaccineId());
        }
    }

    public void checkStep() {
        step = 0;
        if (app.getOptions() != null) {
            if (app.getOptions().getVaccineId() == Options.UNDEFINED) {
                step = 0;
            } else {
                if (app.getOptions().getCenterId() != Options.UNDEFINED) {

                    if (app.getOptions().isSubscriptionPageSeen()) {
                        if (app.getOptions().isControlPageSeen()) {
                            step = 4;
                        } else {
                            step = 3;
                        }
                    } else {
                        step = 2;
                    }
                } else {
                    step = 1;
                }
            }


        }

    }
}
