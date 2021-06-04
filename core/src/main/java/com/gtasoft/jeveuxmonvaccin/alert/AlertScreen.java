package com.gtasoft.jeveuxmonvaccin.alert;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
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

public class AlertScreen implements Screen, ApplicationListener {

    private static String COUNTERFILENAME = "alertcounter.dat";
    Stage stage;
    Skin skin;
    Label lblTitle;


    Label lblCenterName;
    Label lblStats;

    CheckBox cbAlertActivation;
    CheckBox cbAlertDeactivation;

    Label lblIntroExplanations1;
    Label lblIntroExplanations2;
    Label lblIntroExplanations3;
    Label lblIntroExplanations4;
    float w;
    float h;
    float stateTime;
    JeVeuxMonVaccin app;
    boolean translated = false;
    private ImageButton btnBackMenu;


    private OrthographicCamera camera;
    private FitViewport viewport;
    private SpriteBatch sb;


    public AlertScreen(JeVeuxMonVaccin app) {

        this.app = app;


    }

    public static String readFile(String fileName) {
        //System.out.println(" read file " + fileName);
        FileHandle file = Gdx.files.local(fileName);
        if (file != null && file.exists()) {
            String s = file.readString();
            if (s != null && !"".equals(s)) {
                return s.trim();
            }
        }
        return null;
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

        lblTitle = new Label("Alertes", skin);
        lblCenterName = new Label(
                "---", skin);

        lblIntroExplanations1 = new Label("Vous n'obtenez pas de rendez-vous dans votre centre?" +
                " ", skin);


        lblIntroExplanations2 = new Label(
                "Certains téléphones peuvent effectuer la surveillance pour vous." +
                        ""
                , skin);

        lblIntroExplanations3 = new Label(
                "Une notification vous indiquera d'une disponibilité.", skin);
        lblIntroExplanations4 = new Label(
                "(vérification toutes les 15 minutes environ si accès internet)\n", skin);

        lblStats = new Label(" --- ", skin);

        cbAlertActivation = new CheckBox("Alertes actives", skin, "vaccine");
        cbAlertActivation.setChecked(false);
        cbAlertActivation.addListener(new InputListener() {
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if (cbAlertActivation.isChecked()) {
                    cbAlertDeactivation.setChecked(false);
                }
                if (!cbAlertActivation.isChecked()) {
                    cbAlertDeactivation.setChecked(true);
                }

                checkChecked();
            }

            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

        });

        cbAlertDeactivation = new CheckBox("Alertes non actives", skin, "vaccine");
        cbAlertDeactivation.setChecked(false);
        cbAlertDeactivation.addListener(new InputListener() {
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if (cbAlertDeactivation.isChecked()) {
                    cbAlertActivation.setChecked(false);
                }
                if (!cbAlertDeactivation.isChecked()) {
                    cbAlertActivation.setChecked(true);
                }

                checkChecked();
            }

            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

        });


        Label.LabelStyle lblStyleTitle = new Label.LabelStyle();
        lblStyleTitle.fontColor = app.getGraphicTools().getBluetext();
        lblStyleTitle.font = this.skin.getFont("bar-font");
        lblTitle.setStyle(lblStyleTitle);
        lblTitle.setAlignment(Align.center);

        Label.LabelStyle lblStyleIntitule = new Label.LabelStyle();
        //lblStyleIntitule.fontColor = app.getGraphicTools().getBluetext();
        lblStyleIntitule.font = this.skin.getFont("menu");

        Label.LabelStyle lblStyleDetail = new Label.LabelStyle();
        lblStyleDetail.fontColor = app.getGraphicTools().getBluetext();
        lblStyleDetail.font = this.skin.getFont("explications");


        Label.LabelStyle lblStyleStats = new Label.LabelStyle();
        lblStyleStats.fontColor = Color.LIGHT_GRAY;
        lblStyleStats.font = this.skin.getFont("babel");
        lblStats.setStyle(lblStyleStats);

        lblIntroExplanations1.setStyle(lblStyleDetail);
        lblIntroExplanations2.setStyle(lblStyleDetail);
        lblIntroExplanations3.setStyle(lblStyleDetail);
        lblIntroExplanations4.setStyle(lblStyleDetail);

        lblCenterName.setColor(skin.getColor("orange"));
        lblCenterName.setAlignment(Align.center);
        lblCenterName.setStyle(lblStyleIntitule);


        Label.LabelStyle lblStyleInfo = new Label.LabelStyle();
        lblStyleInfo.fontColor = app.getGraphicTools().getBluetext();
        lblStyleInfo.font = this.skin.getFont("explications");


        ImageButton.ImageButtonStyle btnStyle = new ImageButton.ImageButtonStyle();
        btnStyle.up = this.skin.getDrawable("imgBack");
        this.btnBackMenu = new ImageButton(btnStyle);


        btnBackMenu.setSize(96, 96);
        btnBackMenu.setPosition(w / 2 - 96 / 2, hmiddle - 400);
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


        lblTitle.setPosition(w / 2 - lblTitle.getWidth() / 2, h - 150, Align.center);

        lblCenterName.setPosition(w / 2, h / 2 + 320);
        lblCenterName.setAlignment(Align.center);
        lblIntroExplanations1.setPosition(20, h / 2 + 200);

        lblIntroExplanations2.setPosition(20, h / 2 + 100);
        lblIntroExplanations3.setPosition(20, h / 2 + 0);

        cbAlertActivation.setPosition(150, h / 2 - 120);
        cbAlertDeactivation.setPosition(150, h / 2 - 245);

        lblIntroExplanations4.setPosition(20, h / 2 - 500);
        lblStats.setPosition(10, 40);

        stage.setViewport(viewport);
        stage.addActor(btnBackMenu);
        stage.addActor(lblTitle);
        stage.addActor(lblCenterName);

        stage.addActor(cbAlertActivation);
        stage.addActor(cbAlertDeactivation);


        stage.addActor(lblIntroExplanations1);
        stage.addActor(lblIntroExplanations2);
        stage.addActor(lblIntroExplanations3);
        stage.addActor(lblIntroExplanations4);

        stage.addActor(lblStats);


    }

    private void checkChecked() {
        if (cbAlertActivation.isChecked()) {
            app.getOptions().setAlert(true);
            app.getOptions().saveOptions();
            if (app.getOptions().getCenterId() != Options.UNDEFINED && app.getOptions().getVaccineId() != Options.UNDEFINED
                    && app.getMachine() != null && app.getMachine().getSalt() != null) {
                app.initAlert(app.getOptions().getCenterId(), app.getOptions().getVaccineId(), app.getMachine().getSalt(), app.getOptions().getCenterName());
                cbAlertDeactivation.setChecked(false);
            } else {
                lblStats.setText("Alertes impossibles\n Recommencez toutes les étapes éventuellement?");
            }

        } else {
            cbAlertActivation.setChecked(false);
            app.stopAlert();
            app.getOptions().setAlert(false);
            app.getOptions().saveOptions();
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
        //sb.draw(app.getGraphicTools().getImgTitle(), (int) ((w - app.getGraphicTools().getImgTitle().getWidth()) / 2), h - app.getGraphicTools().getImgTitle().getHeight() - 50);
        sb.end();

        displayElements();
        stage.act(delta);
        stage.draw();
    }

    public void displayElements() {
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

        if (sb == null) {
            create();

        }

        Gdx.input.setInputProcessor(stage);
        lblCenterName.setText(app.getOptions().getCenterName());
        lblStats.setText(app.infoAlert());
        if (app.getOptions().isAlert()) {
            cbAlertActivation.setChecked(true);
            cbAlertDeactivation.setChecked(false);
            lblStats.setText(parseStats(app.infoAlert()));
        } else {
            lblStats.setText("");
        }

    }

    public String parseStats(String str) {
        int nb = getStats();
        if (str == null) {
            System.out.println(" stats str null");
            return "Pas d'information pour le moment ou téléphone incompatible.";
        }
        if (nb == -1) {
            System.out.println(" stats nb -1");
            return "Pas d'information pour le moment ou téléphone incompatible.";
        }
        if (str.contains(";")) {
            String val[] = str.split(";");
            if (val.length == 4) {
                if ("true".equals(val[3]) && "true".equals(val[0])) {
                    return " " + nb + " vérifications en arrière plan effectuées.";
                } else {
                    return " " + nb + " vérifications en arrière plan effectuées.\n" +
                            "Un problème est cependant détecté\n" +
                            "Essayez de désactiver puis réactiver les alertes.";

                }

            } else {

                return "Téléphone peut-être incompatible.\n" +
                        "Un problème est détecté\n" +
                        "Essayez de recommencer les étapes 1 à 5?";
            }
        }
        return "Téléphone peut-être incompatible.\n" +
                "Un problème est détecté\n" +
                "Essayez de recommencer les étapes 1 à 5?";
    }

    public int getStats() {
        String str = readFile(COUNTERFILENAME);
        if (str == null) {
            return -1;
        }
        if ("".equals(str)) {
            return -1;
        }
        int l = 0;
        try {
            l = Integer.parseInt(str);

        } catch (Exception e) {
            e.printStackTrace();
            l = -1;
        }
        return l;
    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
        if (app.getOptions() != null) {
            app.getOptions().saveOptions();
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

}
