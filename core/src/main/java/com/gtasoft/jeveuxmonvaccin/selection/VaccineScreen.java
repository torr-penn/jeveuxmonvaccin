package com.gtasoft.jeveuxmonvaccin.selection;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.gtasoft.jeveuxmonvaccin.JeVeuxMonVaccin;
import com.gtasoft.jeveuxmonvaccin.setup.Options;

public class VaccineScreen implements Screen, ApplicationListener {


    //    Texture imgWebsite;
    Stage stage;
    Skin skin;
    Label lblTitle;
    CheckBox cbVaccinePfizer;
    CheckBox cb29;
    CheckBox cb22;
    CheckBox cb35;
    CheckBox cb56;
    Label choixDepartement;
    Label lblInfo;
    Label lbl_continue;
    float w;
    float h;
    float stateTime;
    JeVeuxMonVaccin app;
    boolean translated = false;
    int page = 0;
    private ImageButton btnBackMenu;
    private ImageButton btnNext;
    private OrthographicCamera camera;
    private FitViewport viewport;
    private SpriteBatch sb;

    // constructor to keep a reference to the main Game class
    public VaccineScreen(JeVeuxMonVaccin app) {
        page = 0;
        this.app = app;


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


        int wmiddle = (int) w / 2;
        int hmiddle = (int) h / 2;


        lblTitle = new Label("Vaccination", skin);
        choixDepartement = new Label("Département", skin);
        lbl_continue = new Label("Poursuivre", skin);

        lblInfo = new Label("Cette application accompagne le grand public breton\n " +
                "uniquement pour un vaccin de type ARN-messager", skin);


        Label.LabelStyle lblStyleTitle = new Label.LabelStyle();
        lblStyleTitle.fontColor = app.getGraphicTools().getBluetext();
        lblStyleTitle.font = this.skin.getFont("bar-font");
        lblTitle.setStyle(lblStyleTitle);
        lblTitle.setAlignment(Align.center);

        Label.LabelStyle lblStyleTitlesmall = new Label.LabelStyle();
        lblStyleTitlesmall.fontColor = app.getGraphicTools().getBluetext();
        lblStyleTitlesmall.font = this.skin.getFont("bar-font");

        choixDepartement.setStyle(lblStyleTitlesmall);
        choixDepartement.setScale(0.8f);
        choixDepartement.setAlignment(Align.center);

        Label.LabelStyle lblStyleInfo = new Label.LabelStyle();
        lblStyleInfo.fontColor = app.getGraphicTools().getBluetext();
        lblStyleInfo.font = this.skin.getFont("explications");

        lblInfo.setStyle(lblStyleInfo);
        lblInfo.setAlignment(Align.center);

        lbl_continue.setStyle(lblStyleTitle);
        lbl_continue.setAlignment(Align.center);


        ImageButton.ImageButtonStyle btnStyle = new ImageButton.ImageButtonStyle();
        btnStyle.up = this.skin.getDrawable("imgBack");
        this.btnBackMenu = new ImageButton(btnStyle);


        ImageButton.ImageButtonStyle btnStyleNext = new ImageButton.ImageButtonStyle();
        btnStyleNext.up = this.skin.getDrawable("imgNext");
        this.btnNext = new ImageButton(btnStyleNext);


        cbVaccinePfizer = new CheckBox(" - ARN-Messager\n  (Pfizer/BioNTech ou Moderna)", skin, "vaccine");
        cbVaccinePfizer.setChecked(false);

        cbVaccinePfizer.addListener(new InputListener() {
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                checkChecked();
            }

            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

        });


        cb22 = new CheckBox("  Côtes-d'Armor", skin, "vaccine");
        cb29 = new CheckBox("  Finistère", skin, "vaccine");
        cb35 = new CheckBox("  Ille-et-Vilaine", skin, "vaccine");
        cb56 = new CheckBox("  Morbihan", skin, "vaccine");
        cb22.setChecked(false);
        cb29.setChecked(false);
        cb35.setChecked(false);
        cb56.setChecked(false);
        cb22.addListener(new InputListener() {
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if (cb22.isChecked()) {
                    cb29.setChecked(false);
                    cb35.setChecked(false);
                    cb56.setChecked(false);

                }

                checkChecked();
            }

            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

        });
        cb29.addListener(new InputListener() {
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if (cb29.isChecked()) {
                    cb22.setChecked(false);
                    cb35.setChecked(false);
                    cb56.setChecked(false);

                }
                checkChecked();
            }

            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

        });
        cb35.addListener(new InputListener() {
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if (cb35.isChecked()) {
                    cb22.setChecked(false);
                    cb29.setChecked(false);
                    cb56.setChecked(false);

                }
                checkChecked();
            }

            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

        });
        cb56.addListener(new InputListener() {
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if (cb56.isChecked()) {
                    cb22.setChecked(false);
                    cb29.setChecked(false);
                    cb35.setChecked(false);
                }
                checkChecked();
            }

            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

        });

//        this.imgWebsite = new Texture(Gdx.files.internal("img/selection/go_website_vaccine_choice.png"));
//        this.skin.add("imgWebsiteVaccine", this.imgWebsite);
//
//        ImageButton.ImageButtonStyle btnStylewww = new ImageButton.ImageButtonStyle();
//        btnStylewww.up = this.skin.getDrawable("imgWebsiteVaccine");


        btnBackMenu.setSize(64, 64);
        btnBackMenu.setPosition(w - 24 - 64, h - 24 - 64);
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
                app.setScreen(app.centerScreen);
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
                app.setScreen(app.centerScreen);
                return true;

            }
        });


        lblTitle.setPosition(w / 2 - lblTitle.getWidth() / 2, h - 150, Align.center);

        cbVaccinePfizer.setPosition(w / 6, 3 * h / 4);


        lblInfo.setPosition(w / 2, 3 * h / 4 - 70, Align.center);


        btnNext.setPosition(w / 2 - btnNext.getWidth() / 2, hmiddle - 440);
        lbl_continue.setPosition(w / 2 - lbl_continue.getWidth() / 2, hmiddle - 480);

        choixDepartement.setPosition(w / 2, 4 * h / 7, Align.center);
        cb22.setPosition(w / 8, 3 * h / 7);
        cb29.setPosition(0.55f * w, 3 * h / 7);
        cb35.setPosition(w / 8, 2 * h / 7);
        cb56.setPosition(0.55f * w, 2 * h / 7);
        if (app.getOptions() != null) {
            int d = app.getOptions().getDepartment();
            if (d != Options.UNDEFINED) {
                if (d == 22) {
                    cb22.setChecked(true);
                } else if (d == 29) {
                    cb29.setChecked(true);
                } else if (d == 35) {
                    cb35.setChecked(true);
                } else if (d == 56) {
                    cb56.setChecked(true);
                }
            }
        }


        stage.setViewport(viewport);
        stage.addActor(btnBackMenu);
        stage.addActor(lblInfo);
        stage.addActor(choixDepartement);
        stage.addActor(cbVaccinePfizer);
        stage.addActor(cb22);
        stage.addActor(cb29);
        stage.addActor(cb35);
        stage.addActor(cb56);

        stage.addActor(lblTitle);

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
        //sb.draw(app.getGraphicTools().getImgTitle(), (int) ((w - app.getGraphicTools().getImgTitle().getWidth()) / 2), h - app.getGraphicTools().getImgTitle().getHeight() - 50);
        sb.end();

        displayElements();
        stage.act(delta);
        stage.draw();
    }

    public void displayElements() {
        if (cbVaccinePfizer != null) {
            if (cbVaccinePfizer.isChecked() && (cb22.isChecked() || cb29.isChecked() || cb35.isChecked() || cb56.isChecked())) {

                if (!stage.getActors().contains(btnNext, true)) {
                    stage.addActor(btnNext);
                    stage.addActor(lbl_continue);

                }
            } else {

                if (stage.getActors().contains(btnNext, true)) {
                    btnNext.remove();
                    lbl_continue.remove();
                }
            }
        }
    }

    private void checkChecked() {
        if (cbVaccinePfizer.isChecked()) {
            app.getOptions().setVaccineId(Options.PFIZER);
        } else {
            app.getOptions().setVaccineId(Options.UNDEFINED);
        }
        if (cb22.isChecked()) {
            app.getOptions().setDepartment(22);
        } else if (cb29.isChecked()) {
            app.getOptions().setDepartment(29);
        } else if (cb35.isChecked()) {
            app.getOptions().setDepartment(35);
        } else if (cb56.isChecked()) {
            app.getOptions().setDepartment(56);
        } else {
            app.getOptions().setDepartment(Options.UNDEFINED);
        }

        app.getOptions().setCenterSelected(null);
        app.getOptions().setCenterId(Options.UNDEFINED);
        app.getOptions().setSubscriptionPageSeen(false);
        app.getOptions().setControlPageSeen(false);
        app.getOptions().saveOptions();
        app.centerScreen.setReloadCenter(true);

        //app.centerScreen.emptyCenterBox();


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
        initCheck();
        Gdx.input.setInputProcessor(stage);


    }

    private void initCheck() {
        if (app.getOptions().getVaccineId() != Options.UNDEFINED) {
            if (Options.PFIZER == app.getOptions().getVaccineId()) {
                cbVaccinePfizer.setChecked(true);
            }
        }

    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }


}
