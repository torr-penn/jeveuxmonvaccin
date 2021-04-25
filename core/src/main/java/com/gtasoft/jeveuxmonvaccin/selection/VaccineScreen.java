package com.gtasoft.jeveuxmonvaccin.selection;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
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
    CheckBox cbVaccineAstra;
    CheckBox cbVaccinePfizer;
    CheckBox cbVaccineJanssen;
    Label lblNeedInformation;
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
    private TextButton btnWebsite;
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


        lblTitle = new Label("La vaccination", skin);
        lblNeedInformation = new Label(" En cas de doute : ", skin);
        lbl_continue = new Label("Poursuivre", skin);

        lblInfo = new Label("Je vérifie que je suis bien autorisé à recevoir ce vaccin\n" +
                "Je demande l'aide d'un médecin si ma situation est particulière.", skin);


        Label.LabelStyle lblStyleTitle = new Label.LabelStyle();
        lblStyleTitle.fontColor = Color.WHITE;
        lblStyleTitle.font = this.skin.getFont("bar-font");
        lblTitle.setStyle(lblStyleTitle);
        lblTitle.setAlignment(Align.center);

        Label.LabelStyle lblStyleInfo = new Label.LabelStyle();
        lblStyleInfo.fontColor = Color.WHITE;
        lblStyleInfo.font = this.skin.getFont("explications");
        lblNeedInformation.setStyle(lblStyleInfo);
        lblInfo.setStyle(lblStyleInfo);

        lbl_continue.setStyle(lblStyleTitle);
        lbl_continue.setAlignment(Align.center);


        ImageButton.ImageButtonStyle btnStyle = new ImageButton.ImageButtonStyle();
        btnStyle.up = this.skin.getDrawable("imgBack");
        this.btnBackMenu = new ImageButton(btnStyle);


        ImageButton.ImageButtonStyle btnStyleNext = new ImageButton.ImageButtonStyle();
        btnStyleNext.up = this.skin.getDrawable("imgNext");
        this.btnNext = new ImageButton(btnStyleNext);


        cbVaccineAstra = new CheckBox(" - AstraZeneca", skin, "vaccine");
        cbVaccineAstra.setChecked(false);
        cbVaccinePfizer = new CheckBox(" - ARN-Messager\n  (Pfizer/BioNTech ou Moderna)", skin, "vaccine");
        cbVaccinePfizer.setChecked(false);

        cbVaccineJanssen = new CheckBox(" - Janssen", skin, "vaccine");
        cbVaccineJanssen.setChecked(false);

        cbVaccineAstra.addListener(new InputListener() {
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if (cbVaccineAstra.isChecked()) {
                    cbVaccinePfizer.setChecked(false);
                    cbVaccineJanssen.setChecked(false);
                }
                checkChecked();
            }

            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

        });
        cbVaccinePfizer.addListener(new InputListener() {
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if (cbVaccinePfizer.isChecked()) {
                    cbVaccineJanssen.setChecked(false);
                    cbVaccineAstra.setChecked(false);
                }
                checkChecked();
            }

            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

        });
        cbVaccineJanssen.addListener(new InputListener() {
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if (cbVaccineJanssen.isChecked()) {
                    cbVaccinePfizer.setChecked(false);
                    cbVaccineAstra.setChecked(false);

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
        this.btnWebsite = new TextButton("Je m'informe\n depuis sante.fr", skin, "link");


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


        btnWebsite.setSize(390, 96);
        this.btnWebsite.addListener(new ClickListener() {
            @Override
            public void touchUp(InputEvent e, float x, float y, int point, int button) {
                return;
            }

            @Override
            public boolean touchDown(InputEvent e, float x, float y, int point, int button) {
                Gdx.net.openURI("https://www.sante.fr/la-vaccination-covid-19-pour-le-grand-public");
                return true;
            }
        });

        lblTitle.setPosition(w / 2 - lblTitle.getWidth() / 2, h - 150, Align.center);

        cbVaccinePfizer.setPosition(w / 6, h / 2 + 225);
        cbVaccineAstra.setPosition(w / 6, h / 2 + 100);
        cbVaccineJanssen.setPosition(w / 6, h / 2 - 25);


        lblInfo.setPosition(20, h / 2 - 70);


        btnNext.setPosition(w / 2 - btnNext.getWidth() / 2, hmiddle - 440);
        lbl_continue.setPosition(w / 2 - lbl_continue.getWidth() / 2, hmiddle - 480);

        lblNeedInformation.setPosition(w / 4, 50 + h / 2 - 250 + 45);
        btnWebsite.setPosition(w / 4, h / 2 - 250);


        stage.setViewport(viewport);
        stage.addActor(btnBackMenu);
        stage.addActor(btnWebsite);
        stage.addActor(lblNeedInformation);
        //  stage.addActor(cbVaccineAstra);
        stage.addActor(cbVaccinePfizer);
        //stage.addActor(cbVaccineJanssen);
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
        if (cbVaccineAstra != null) {
            if (cbVaccineAstra.isChecked() || cbVaccinePfizer.isChecked() || cbVaccineJanssen.isChecked()) {
                if (!stage.getActors().contains(lblInfo, true)) {
                    stage.addActor(lblInfo);
                }
                if (!stage.getActors().contains(btnNext, true)) {
                    stage.addActor(btnNext);
                    stage.addActor(lbl_continue);

                }
            } else {
                if (stage.getActors().contains(lblInfo, true)) {
                    lblInfo.remove();
                }
                if (stage.getActors().contains(btnNext, true)) {
                    btnNext.remove();
                    lbl_continue.remove();
                }
            }
        }
    }

    private void checkChecked() {
        if (cbVaccineAstra.isChecked()) {
            app.getOptions().setVaccineId(Options.ASTRAZENECA);
        } else if (cbVaccinePfizer.isChecked()) {
            app.getOptions().setVaccineId(Options.PFIZER);

        } else if (cbVaccineJanssen.isChecked()) {
            app.getOptions().setVaccineId(Options.JANSSEN);

        } else {
            app.getOptions().setVaccineId(Options.UNDEFINED);
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
                cbVaccineAstra.setChecked(false);
                cbVaccinePfizer.setChecked(true);
                cbVaccineJanssen.setChecked(false);
            } else if (Options.ASTRAZENECA == app.getOptions().getVaccineId()) {
                cbVaccineAstra.setChecked(true);
                cbVaccinePfizer.setChecked(false);
                cbVaccineJanssen.setChecked(false);
            } else if (Options.JANSSEN == app.getOptions().getVaccineId()) {
                cbVaccineAstra.setChecked(false);
                cbVaccinePfizer.setChecked(false);
                cbVaccineJanssen.setChecked(true);
            }
        }

    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }


}
