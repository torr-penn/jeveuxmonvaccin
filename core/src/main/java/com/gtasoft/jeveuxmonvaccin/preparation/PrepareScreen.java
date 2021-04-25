package com.gtasoft.jeveuxmonvaccin.preparation;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
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
import com.gtasoft.jeveuxmonvaccin.center.VaccinationCenter;
import com.gtasoft.jeveuxmonvaccin.setup.Options;

import java.text.NumberFormat;

public class PrepareScreen implements Screen, ApplicationListener {


    Stage stage;
    Skin skin;
    Label lblTitle;


    Label lblCenterName;

    String URL_SUBSCRIBE_MAIIA = "https://www.maiia.com/register";
    String URL_SUBSCRIBE_DOCTOLIB = "https://www.doctolib.fr/sessions/new";
    String URL_SUBSCRIBE_KElDOC = "https://www.keldoc.com";

    Texture imgbg;
    Label lblInfoCenter1;
    Label lblInfoCenter2;
    Label lblInfoCenter3;

    Label lblIntroExplanations1;
    Label lblIntroExplanations2;
    Label lblSubscribeHelp;
    Label lblIntroExplanations4;

    Label lbl_continue;

    Label lblManagedBy;

    //   TextButton tbPlatformLink;
    TextButton tbSubscribeLink;

    float w;
    boolean cname = false;
    float h;
    float stateTime;
    JeVeuxMonVaccin app;
    boolean translated = false;
    private ImageButton btnBackMenu;
    private ImageButton btnNext;

    private ImageButton btnWebsiteD;
    private Texture imageWebsiteD;
    private ImageButton btnWebsiteM;
    private Texture imageWebsiteM;
    private ImageButton btnWebsiteK;
    private Texture imageWebsiteK;
    private OrthographicCamera camera;
    private FitViewport viewport;
    private SpriteBatch sb;


    public PrepareScreen(JeVeuxMonVaccin app) {
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
        imgbg = new Texture(Gdx.files.internal("img/preparation/infos_bg.png"));

        tbSubscribeLink = new TextButton("S'inscrire", skin, "link");

        lblTitle = new Label("Préparation", skin);
        lbl_continue = new Label("Poursuivre", skin);

        lblIntroExplanations1 = new Label("Si vous n'avez pas trouvé de rendez-vous,\n" +
                "cette application va vous aider pour le centre sélectionné :" +
                " ", skin);

        lblCenterName = new Label("", skin);

        lblIntroExplanations2 = new Label(
                "En premier conseil inscrivez vous déjà sur Doctolib \nvous serez ainsi plus rapide quand une place va se libérer."
                , skin);


        lblSubscribeHelp = new Label("" + "", skin);

        lblIntroExplanations4 = new Label(
                " Préparation de la surveillance : En attente", skin);

        lblInfoCenter1 = new Label(" Il y a un rendez vous possible pour  ... % des recherches.", skin);
        lblInfoCenter2 = new Label("Dernière opportunité : ...", skin);
        lblInfoCenter3 = new Label("Fermeture du centre : ...", skin);

        Label.LabelStyle lblStyleTitle = new Label.LabelStyle();
        lblStyleTitle.fontColor = Color.WHITE;
        lblStyleTitle.font = this.skin.getFont("bar-font");
        lblTitle.setStyle(lblStyleTitle);
        lblTitle.setAlignment(Align.center);

        Label.LabelStyle lblStyleIntitule = new Label.LabelStyle();
        lblStyleIntitule.fontColor = Color.WHITE;
        lblStyleIntitule.font = this.skin.getFont("menu");

        Label.LabelStyle lblStyleDetail = new Label.LabelStyle();
        lblStyleDetail.fontColor = Color.WHITE;
        lblStyleDetail.font = this.skin.getFont("explications");

        Label.LabelStyle lblStyleInfos = new Label.LabelStyle();
        lblStyleInfos.fontColor = Color.WHITE;
        lblStyleInfos.font = this.skin.getFont("babel");

        lblInfoCenter1.setStyle(lblStyleInfos);
        lblInfoCenter2.setStyle(lblStyleInfos);
        lblInfoCenter3.setStyle(lblStyleInfos);

        lblIntroExplanations1.setStyle(lblStyleDetail);
        lblIntroExplanations2.setStyle(lblStyleDetail);
        lblSubscribeHelp.setStyle(lblStyleDetail);
        lblIntroExplanations4.setStyle(lblStyleDetail);

        lblCenterName.setColor(skin.getColor("yellow"));
        lblCenterName.setAlignment(Align.center);
        lblCenterName.setStyle(lblStyleIntitule);

        lblManagedBy = new Label("", skin);
        lblManagedBy.setColor(Color.WHITE);
        lblManagedBy.setStyle(lblStyleDetail);


        Label.LabelStyle lblStyleInfo = new Label.LabelStyle();
        lblStyleInfo.fontColor = Color.WHITE;
        lblStyleInfo.font = this.skin.getFont("explications");

        lbl_continue.setStyle(lblStyleTitle);
        lbl_continue.setAlignment(Align.center);


        ImageButton.ImageButtonStyle btnStyle = new ImageButton.ImageButtonStyle();
        btnStyle.up = this.skin.getDrawable("imgBack");
        this.btnBackMenu = new ImageButton(btnStyle);


        ImageButton.ImageButtonStyle btnStyleNext = new ImageButton.ImageButtonStyle();
        btnStyleNext.up = this.skin.getDrawable("imgNext");
        this.btnNext = new ImageButton(btnStyleNext);


        this.imageWebsiteD = new Texture(Gdx.files.internal("img/selection/doctolib250x80.png"));
        this.skin.add("imgWebsiteD", this.imageWebsiteD);

        ImageButton.ImageButtonStyle btnStylewd = new ImageButton.ImageButtonStyle();
        btnStylewd.up = this.skin.getDrawable("imgWebsiteD");
        this.btnWebsiteD = new ImageButton(btnStylewd);

        this.imageWebsiteM = new Texture(Gdx.files.internal("img/selection/maiia250x80.png"));
        this.skin.add("imgWebsiteM", this.imageWebsiteM);

        ImageButton.ImageButtonStyle btnStylewm = new ImageButton.ImageButtonStyle();
        btnStylewm.up = this.skin.getDrawable("imgWebsiteM");
        this.btnWebsiteM = new ImageButton(btnStylewm);

        this.imageWebsiteK = new Texture(Gdx.files.internal("img/selection/keldoc250x80.png"));
        this.skin.add("imgWebsiteK", this.imageWebsiteK);

        ImageButton.ImageButtonStyle btnStylewk = new ImageButton.ImageButtonStyle();
        btnStylewk.up = this.skin.getDrawable("imgWebsiteK");
        this.btnWebsiteK = new ImageButton(btnStylewk);

        btnWebsiteD.setSize(250, 80);
        btnWebsiteK.setSize(250, 80);
        btnWebsiteM.setSize(250, 80);


        tbSubscribeLink.addListener(new ClickListener() {
            @Override
            public void touchUp(InputEvent e, float x, float y, int point, int button) {


                return;
            }

            @Override
            public boolean touchDown(InputEvent e, float x, float y, int point, int button) {
                goSubscribeLink();
                return true;

            }
        });


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
                app.getOptions().setSubscriptionPageSeen(true);
                app.setScreen(app.controlScreen);
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
                app.getOptions().setSubscriptionPageSeen(true);
                app.setScreen(app.controlScreen);
                return true;

            }
        });
        this.btnWebsiteM.addListener(new ClickListener() {
            @Override
            public void touchUp(InputEvent e, float x, float y, int point, int button) {


                return;
            }

            @Override
            public boolean touchDown(InputEvent e, float x, float y, int point, int button) {
                Gdx.net.openURI(URL_SUBSCRIBE_MAIIA);

                return true;

            }
        });
        this.btnWebsiteD.addListener(new ClickListener() {
            @Override
            public void touchUp(InputEvent e, float x, float y, int point, int button) {


                return;
            }

            @Override
            public boolean touchDown(InputEvent e, float x, float y, int point, int button) {
                Gdx.net.openURI(URL_SUBSCRIBE_DOCTOLIB);
                return true;

            }
        });
        this.btnWebsiteK.addListener(new ClickListener() {
            @Override
            public void touchUp(InputEvent e, float x, float y, int point, int button) {


                return;
            }

            @Override
            public boolean touchDown(InputEvent e, float x, float y, int point, int button) {
                Gdx.net.openURI(URL_SUBSCRIBE_KElDOC);
                return true;

            }
        });

        tbSubscribeLink.setSize(280, 80);


        lblTitle.setPosition(w / 2 - lblTitle.getWidth() / 2, h - 150, Align.center);

        lblIntroExplanations1.setPosition(20, h / 2 + 360);
        lblCenterName.setPosition(w / 2, h / 2 + 260);
        lblInfoCenter1.setPosition(50, h / 2 + 180, Align.left);
        lblInfoCenter2.setPosition(50, h / 2 + 145, Align.left);
        lblInfoCenter3.setPosition(50, h / 2 + 110, Align.left);

        lblIntroExplanations2.setPosition(20, h / 2 - 30);
        lblSubscribeHelp.setPosition(40, h / 2 - 210);

        tbSubscribeLink.setPosition(w / 4 - tbSubscribeLink.getWidth() / 2, hmiddle - 160);
        btnWebsiteD.setPosition(3 * w / 4 - btnWebsiteD.getWidth() / 2, h / 2 - 150);
        btnWebsiteK.setPosition(3 * w / 4 - btnWebsiteK.getWidth() / 2, h / 2 - 150);
        btnWebsiteM.setPosition(3 * w / 4 - btnWebsiteM.getWidth() / 2, h / 2 - 150);
        lblManagedBy.setPosition(w / 7, h / 2 - 130);

        btnNext.setPosition(w / 2 - btnNext.getWidth() / 2, hmiddle - 440);
        lbl_continue.setPosition(w / 2 - lbl_continue.getWidth() / 2, hmiddle - 480);


        stage.setViewport(viewport);
        stage.addActor(btnBackMenu);
        stage.addActor(lblTitle);


        stage.addActor(lblIntroExplanations1);
        stage.addActor(tbSubscribeLink);
        stage.addActor(lblCenterName);


        stage.addActor(lblIntroExplanations2);

        stage.addActor(lblSubscribeHelp);
        stage.addActor(btnNext);
        stage.addActor(lbl_continue);


    }

    @Override
    public void render() {

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
        sb.draw(imgbg, 15, h / 2 + 80);
        sb.end();

        displayElements();
        stage.act(delta);
        stage.draw();
    }

    public void displayElements() {

        if ("".equals(lblCenterName.getText()) && app.getOptions().getCenterSelected() != null) {
            lblCenterName.setText(app.getOptions().getCenterSelected().getName() + " name");
        }


        CenterTools ct = app.getCenterTools();
        if (ct.getRegisterStatus() < 0) {
            lblIntroExplanations4.setText(ct.getRegisterMsg());
        }
        if (ct.getInfoStatus() == CenterTools.LOADED) {
            if (ct.getInfoPct() == -1) {

                lblInfoCenter1.setText(ct.getInfoMsg());
                showit(lblInfoCenter1);
                hideit(lblInfoCenter2);
                hideit(lblInfoCenter3);
            } else {
                lblInfoCenter1.setText("Informations du centre : " + ct.getInfoMsg());
                lblInfoCenter2.setText("" + getDisplayablePct(ct.getInfoPct()) + "% chances de rdv observés");
                showit(lblInfoCenter1);
                showit(lblInfoCenter2);

                if (!"null".equals(ct.getInfoClosing())) {
                    showit(lblInfoCenter3);
                    lblInfoCenter3.setText("Plus récente disponibilité :" + ct.getInfoLastok() + "");
                } else {
                    hideit(lblInfoCenter3);
                }
            }
            ct.setInfoStatus(CenterTools.NO_LOAD);
        }
    }

    private String getDisplayablePct(float pct) {
        float p = pct * 100f;
        if (p > 5.0f) {
            return ((int) p) + "";
        }
        if (p > 1.0f) {
            NumberFormat nf = NumberFormat.getInstance();
            nf.setMaximumFractionDigits(1);


            return nf.format(p);
        }
        NumberFormat nf = NumberFormat.getInstance();
        nf.setMinimumIntegerDigits(1);
        nf.setMaximumFractionDigits(3);
        return nf.format(p);

    }


    private boolean availableLink() {
        if (app.getOptions() != null) {
            if (app.getOptions().getCenterSelected() != null && app.getOptions().getCenterSelected().getLink() != null) {
                return true;
            }
        }
        return false;
    }


    private void goCenterLink() {
        if (availableLink()) {
            Gdx.net.openURI(app.getOptions().getCenterSelected().getLink().trim());
        }
    }

    private void goSubscribeLink() {
        if (app.getOptions().getCenterSelected().getProviderId() == VaccinationCenter.MAIIA_ID) {
            Gdx.net.openURI(URL_SUBSCRIBE_MAIIA);

        } else if (app.getOptions().getCenterSelected().getProviderId() == VaccinationCenter.DOCTOLIB_ID) {
            Gdx.net.openURI(URL_SUBSCRIBE_DOCTOLIB);
        } else if (app.getOptions().getCenterSelected().getProviderId() == VaccinationCenter.KELDOC_ID) {
            Gdx.net.openURI(URL_SUBSCRIBE_KElDOC);
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

        if (sb == null) {
            create();

        }

        Gdx.input.setInputProcessor(stage);
        if (app.getOptions().getCenterSelected() != null) {

            if (app.getOptions().getCenterSelected().getProviderId() == VaccinationCenter.MAIIA_ID) {
                lblIntroExplanations2.setText("En premier conseil inscrivez vous déjà sur Maiia, \nvous serez ainsi plus rapide quand une place va se libérer.");
                showit(btnWebsiteM);
                hideit(btnWebsiteD);
                hideit(btnWebsiteK);
                lblSubscribeHelp.setText("L'inscription nécessite un numéro de téléphone.");

            } else if (app.getOptions().getCenterSelected().getProviderId() == VaccinationCenter.DOCTOLIB_ID) {
                lblIntroExplanations2.setText("En premier conseil inscrivez vous déjà sur Doctolib, \nvous serez ainsi plus rapide quand une place va se libérer.");

                showit(btnWebsiteD);
                hideit(btnWebsiteM);
                hideit(btnWebsiteK);
                lblSubscribeHelp.setText("Pour vous inscrire, choisissez : \n\"Nouveau sur Doctolib?\" -> S'inscrire.");

            } else if (app.getOptions().getCenterSelected().getProviderId() == VaccinationCenter.KELDOC_ID) {
                lblIntroExplanations2.setText("En premier conseil inscrivez vous déjà sur KelDoc, \nvous serez ainsi plus rapide quand une place va se libérer.");
                showit(btnWebsiteK);
                hideit(btnWebsiteM);
                hideit(btnWebsiteD);
                lblSubscribeHelp.setText("Pour vous inscrire, choisissez : \n\"Mon Compte- Connexion\" -> S'inscrire.");

            } else {
                hideit(btnWebsiteK);
                hideit(btnWebsiteM);
                hideit(btnWebsiteD);
                lblSubscribeHelp.setText("");

            }
        }
        if (app.getOptions() != null) {
            if (!"".equals(app.getOptions().getCenterName())) {
                lblCenterName.setText(app.getOptions().getCenterName());
            }
        }
        if (app.getOptions().getCenterId() != Options.UNDEFINED) {
            app.getCenterTools().selectCenter(app.getOptions().getCenterId(), app.getOptions().getVaccineId());
        }
        infoCenter();


        registerCenter();
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

    private void registerCenter() {
        if (!app.getOptions().isCenterRegistered()) {
            app.getCenterTools().registerCenter();
            lblIntroExplanations4.setText(" Demande en cours ");
        }
    }

    private void infoCenter() {
        if (app.getOptions() != null) {
            app.getCenterTools().infoCenter();


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
