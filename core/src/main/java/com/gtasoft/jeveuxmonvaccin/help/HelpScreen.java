package com.gtasoft.jeveuxmonvaccin.help;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
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
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.gtasoft.jeveuxmonvaccin.JeVeuxMonVaccin;

public class HelpScreen implements Screen, ApplicationListener {


    private static ImageButton btnBackMenu;

    private static ImageButton btnWebsite;
    private static ImageButton btnVideo;
    Texture imgWebsite;
    Texture imgVideo;

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
                "Un grand merci aux responsables de ces plateformes \n" +
                "de ne pas interdire cette application.\n" +
                "Votre rendez-vous sera pris sur ces sites.", skin);

        Label.LabelStyle lblStylePlay = new Label.LabelStyle();
        lblStylePlay.fontColor = Color.WHITE;
        lblStylePlay.font = this.skin.getFont("bar-font");
        lblTitle.setStyle(lblStylePlay);


        Label.LabelStyle lblStyleMesg = new Label.LabelStyle();
        lblStyleMesg.fontColor = Color.WHITE;
        lblStyleMesg.font = this.skin.getFont("explications");
        lblMesgTop.setStyle(lblStyleMesg);
        lblMesgTop.setAlignment(Align.left);
        lblMesgBottom.setStyle(lblStyleMesg);
        lblMesgBottom.setAlignment(Align.left);

        ImageButton.ImageButtonStyle btnStyle = new ImageButton.ImageButtonStyle();
        btnStyle.up = this.skin.getDrawable("imgBack");
        this.btnBackMenu = new ImageButton(btnStyle);

        this.imgWebsite = new Texture(Gdx.files.internal("img/help/go_website.png"));
        this.skin.add("imgWebsite", this.imgWebsite);
        ImageButton.ImageButtonStyle btnStylew = new ImageButton.ImageButtonStyle();
        btnStylew.up = this.skin.getDrawable("imgWebsite");
        this.btnWebsite = new ImageButton(btnStylew);

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
                Gdx.net.openURI("https://www.torr-penn.bzh/index.php?lang=fr");
                return true;

            }
        });

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
        } else {
            if (stage.getActors().contains(btnVideo, true)) {
                btnVideo.remove();
            }
        }

        if (page == 3) {
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
                    lblTitle.setText("Origines ");
                    lblMesgTop.setText("");
                    lblMesgBottom.setText(
                            "L'idée est venue de l'observation qu'il faillait parfois \n" +
                                    "des jours pour avoir la chance de trouver un créneau \n" +
                                    "de vaccination dans le centre proche de chez soi.\n\n" +
                                    "Mes parents ayant plus de 70 ans, ils n'étaient pas armés \n" +
                                    "pour attendre devant leur écran une disponibilité.\n\n" +
                                    "Cette application n'est pas idéale non plus, il faut avoir\n" +
                                    "une connaissance des applications mobiles pour pouvoir\n" +
                                    "l'installer et l'utiliser...\n\n\n" +
                                    "L'ambition est de vous aider à attendre et à prendre \n" +
                                    "rendez-vous au bon moment.\n" +
                                    "L'espoir est que vite vous puissiez désinstaller \n" +
                                    "cette application une fois vacciné!\n\n" +
                                    "Cette application est totalement bénévole.");

                } else if (page == 1) {
                    page = 2;
                    lblTitle.setText("Limitations");
                    lblMesgTop.setText("\n\n\n Remarque :\n\n" +
                            "Pour que tout le monde ait sa chance, quand une place\n" +
                            "se libère, tous les téléphones ne reçoivent pas l'information.\n\n " +
                            "Vous comprenez qu'il est inutile que 100 personnes stressent \n" +
                            "et essaient d'avoir une seule place disponible en même temps."
                    );
                    lblMesgBottom.setText(
                            "Tout le monde a la même chance d'avoir l'information.\n\n" +
                                    "Cette  application est proposée au lancement :\n" +
                                    "  - uniquement en Bretagne\n" +
                                    "  - uniquement sur Android\n" +
                                    "\n\n Attention : \n" +
                                    "L'application ne fonctionne que lorsque vous \n" +
                                    "avez une connexion internet active.");

                } else if (page == 2) {
                    page = 3;
                    lblMesgTop.setText("\n\n\nLe code de l'application mobile est libre. \n" +
                            "Application éphémère  très bientôt inutile espérons-le.\n" +
                            "Une version PC est possible depuis le code source.\n"
                    );
                    lblMesgBottom.setText("Pour toute question : \ne-mail : contact@torr-penn.bzh\n" +
                            "\n" +
                            "Pour une réaction, une remarque, \npour exprimer la joie d'avoir obtenu un rendez-vous :\n" +
                            "Twitter : https://twitter.com/TorrpennBzh \n\n" +
                            "Merci!\n\n" +
                            " Pour découvrir mes autres applications :\n");

                } else if (page == 3) {
                    page = 0;
                    app.setScreen(app.mainMenuScreen);

                }

                return;
            }

        }


        sb.begin();
        sb.draw(app.getGraphicTools().getImgBackground(), 0, 0, w, h, 0, 20, 12, 0);
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
                            "Merci aux responsables de ces plateformes de ne pas\n" +
                            "interdire cette application.\n" +
                            "Votre rendez-vous sera pris sur ces sites.");

        }
        Gdx.input.setInputProcessor(stage);


    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }


}
