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
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.gtasoft.jeveuxmonvaccin.JeVeuxMonVaccin;

public class HelpScreen implements Screen, ApplicationListener {


    private static ImageButton btnBackMenu;

    private static ImageButton btnWebsite;
    private static TextButton btnSante;
    private static ImageButton btnVideo;
    Texture imgWebsite;
    Texture imgVideo;

    Texture imgPresentation;

    Stage stage;
    Skin skin;

    Label lblTitle;
    Label lblMesgTop;
    Label lblMesgBottom;
    float w;
    float h;
    float stateTime;
    JeVeuxMonVaccin app;
    boolean translated = false;
    int page = 0;
    private OrthographicCamera camera;
    private FitViewport viewport;
    private SpriteBatch sb;

    // constructor to keep a reference to the main Game class
    public HelpScreen(JeVeuxMonVaccin app) {
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
        lblTitle = new Label("Aide", skin);
        lblMesgTop = new Label("Suivez cette vidéo pour apprendre à utiliser " +
                "l'application :", skin);
        lblMesgBottom = new Label("Cette application n'est pas officielle.\n" +
                "Elle est réalisée en soutien et complément gratuit \n" +
                "aux sites professionnels : Doctolib, KelDoc et Maiia.\n" +
                "Merci à ces plateformes !\n" +
                "Votre rendez-vous sera pris sur ces sites.\n\n\n" +
                "Sante.fr vous informe sur les vaccins, leur fonctionnement\n" +
                "leur utilité et la stratégie de vaccination ", skin);

        Label.LabelStyle lblStylePlay = new Label.LabelStyle();
        lblStylePlay.fontColor = app.getGraphicTools().getBluetext();
        lblStylePlay.font = this.skin.getFont("bar-font");
        lblTitle.setStyle(lblStylePlay);


        Label.LabelStyle lblStyleMesg = new Label.LabelStyle();
        lblStyleMesg.fontColor = app.getGraphicTools().getBluetext();
        lblStyleMesg.font = this.skin.getFont("explications");
        lblMesgTop.setStyle(lblStyleMesg);
        lblMesgTop.setAlignment(Align.left);
        lblMesgBottom.setStyle(lblStyleMesg);
        lblMesgBottom.setAlignment(Align.left);

        btnSante = new TextButton("Sante.fr", skin, "link");


        ImageButton.ImageButtonStyle btnStyle = new ImageButton.ImageButtonStyle();
        btnStyle.up = this.skin.getDrawable("imgBack");
        this.btnBackMenu = new ImageButton(btnStyle);

        this.imgWebsite = new Texture(Gdx.files.internal("img/help/go_website.png"));
        this.skin.add("imgWebsite", this.imgWebsite);
        ImageButton.ImageButtonStyle btnStylew = new ImageButton.ImageButtonStyle();
        btnStylew.up = this.skin.getDrawable("imgWebsite");
        this.btnWebsite = new ImageButton(btnStylew);

        this.imgPresentation = new Texture(Gdx.files.internal("img/presentation.png"));


        this.imgVideo = new Texture(Gdx.files.internal("img/help/go_video.png"));
        this.skin.add("imgVideo", this.imgVideo);
        ImageButton.ImageButtonStyle btnStylev = new ImageButton.ImageButtonStyle();
        btnStylev.up = this.skin.getDrawable("imgVideo");
        this.btnVideo = new ImageButton(btnStylev);


        stage = new Stage();
        camera = new OrthographicCamera();
        viewport = new FitViewport(w, h, camera);


        int wmiddle = (int) w / 2;
        int hmiddle = (int) h / 2;


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

        btnVideo.setSize(394, 96);
        this.btnVideo.addListener(new ClickListener() {
            @Override
            public void touchUp(InputEvent e, float x, float y, int point, int button) {


                return;
            }

            @Override
            public boolean touchDown(InputEvent e, float x, float y, int point, int button) {
                Gdx.net.openURI("http://torr-penn.bzh/video_jeveuxmonvaccin.php");
                return true;

            }
        });
        btnWebsite.setSize(350, 96);
        this.btnWebsite.addListener(new ClickListener() {
            @Override
            public void touchUp(InputEvent e, float x, float y, int point, int button) {


                return;
            }

            @Override
            public boolean touchDown(InputEvent e, float x, float y, int point, int button) {
                Gdx.net.openURI("https://www.torr-penn.bzh/jvmv.php");
                return true;

            }
        });
        btnSante.addListener(new ClickListener() {
            @Override
            public void touchUp(InputEvent e, float x, float y, int point, int button) {


                return;
            }

            @Override
            public boolean touchDown(InputEvent e, float x, float y, int point, int button) {
                Gdx.net.openURI("https://www.gouvernement.fr/info-coronavirus/vaccins");
                return true;

            }
        });

        btnSante.setPosition(w / 2, h / 2 - 205, Align.center);
        lblTitle.setPosition(50, h - 150);
        lblMesgTop.setPosition(40, h - 250);
        lblMesgBottom.setPosition(40, h / 2 - 100);

        btnWebsite.setPosition((w - 350) / 2, h / 2 - 300);
        btnBackMenu.setPosition(w - 24 - 64, h - 24 - 64);
        btnVideo.setPosition((w - 394) / 2, 3 * h / 4 - 100);

        stage.setViewport(viewport);
        stage.addActor(btnBackMenu);
        stage.addActor(lblTitle);
        stage.addActor(lblMesgTop);
        stage.addActor(lblMesgBottom);

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
        if (page == 0) {
            if (!stage.getActors().contains(btnVideo, true)) {
                stage.addActor(btnVideo);
            }
            if (!stage.getActors().contains(btnSante, true)) {
                stage.addActor(btnSante);
            }

        } else {
            if (stage.getActors().contains(btnVideo, true)) {
                btnVideo.remove();
            }
            if (stage.getActors().contains(btnSante, true)) {
                btnSante.remove();
            }
        }

        if (page == 2) {
            if (!stage.getActors().contains(btnWebsite, true)) {
                stage.addActor(btnWebsite);
            }
        } else {
            if (stage.getActors().contains(btnWebsite, true)) {
                btnWebsite.remove();
            }
        }

        camera.update();
        sb.setProjectionMatrix(camera.combined);
        if (Gdx.input.justTouched()) {
            //    System.out.println(" x : " + Gdx.input.getX() + "  and y " + Gdx.input.getY() + " w-24-32 " + (w - 24 - 32) + " h-24-32 " + (h - 24 - 32));
            if (Gdx.input.getX() < w - 24 - 32 || Gdx.input.getY() > 24 + 32) {
                if (page == 0) {
                    page = 1;
                    lblTitle.setText("Objectif ");
                    lblMesgTop.setText("");
                    lblMesgBottom.setText(
                            "L'idée est venue de l'observation qu'il faillait parfois \n" +
                                    "des jours pour avoir la chance de trouver un créneau \n" +
                                    "de vaccination dans le centre proche de chez soi.\n\n" +
                                    "Quand on ne voit pas de disponibilités, on ne sait pas  \n" +
                                    "trop à quel point cela sera compliqué.\n" +
                                    "Les informations de cette applications sont là pour vous\n" +
                                    "aider dans votre démarche à ne pas abandonner.\n\n\n" +
                                    "L'ambition est de vous aider à attendre et à prendre \n" +
                                    "rendez-vous au bon moment.\n" +
                                    "L'espoir est que vite vous puissiez désinstaller \n" +
                                    "cette application une fois vacciné!\n\n" +
                                    "Cette application est totalement gratuite.");

                } else if (page == 1) {
                    page = 2;
                    lblMesgTop.setText("\n\n\nLe code de l'application mobile est libre. \n" +
                            "L'application ne fonctionne que lorsque vous \n" +
                            "avez une connexion internet active.");

                    lblMesgBottom.setText("Pour toute question : \ne-mail : contact@torr-penn.bzh\n" +
                            "\n" +
                            "Une version web de cette application été réalisée \n" +
                            "fin avril en attendant sa publication/validation. \n\n");

                } else if (page == 2) {
                    page = 0;
                    app.setScreen(app.mainMenuScreen);

                }

                return;
            }

        }


        sb.begin();
        sb.draw(app.getGraphicTools().getImgBackground(), 0, 0, w, h, 0, 20, 12, 0);
        if (page == 0) {
            sb.draw(imgPresentation, 5, 5);
        }
        sb.draw(app.getGraphicTools().getImgNext(), w - 100, 4);
        sb.end();
        stage.act(delta);
        stage.draw();
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

        } else {
            lblTitle.setText("Aide");

            lblMesgTop.setText("Suivez cette vidéo pour apprendre à utiliser " +
                    "l'application :");
            lblMesgBottom.setText(
                    "Cette application n'est pas officielle.\n" +
                            "Elle est réalisée en soutien et complément gratuit \n" +
                            "aux sites professionnels : Doctolib, KelDoc et Maiia.\n" +
                            "Merci à ces plateformes !\n" +
                            "Votre rendez-vous sera pris sur ces sites.\n\n\n" +
                            "Sante.fr vous informe sur les vaccins, leur fonctionnement\n" +
                            "leur utilité et la stratégie de vaccination en France ");

        }
        Gdx.input.setInputProcessor(stage);


    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }


}
