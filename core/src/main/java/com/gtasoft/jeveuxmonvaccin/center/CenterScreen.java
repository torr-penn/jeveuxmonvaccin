package com.gtasoft.jeveuxmonvaccin.center;

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
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.gtasoft.jeveuxmonvaccin.JeVeuxMonVaccin;
import com.gtasoft.jeveuxmonvaccin.setup.Options;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.Timer;
import java.util.TimerTask;

public class CenterScreen implements Screen, ApplicationListener {

    private static String PHONE_REGISTER = "http://www.torr-penn.com/jeveuxmonvaccin/phoneRegister";
    Stage stage;
    Skin skin;
    Label lblTitle;
    Label lblNoInternet;
    SelectBox<VaccinationCenter> centerBox;
    int centerdep = 0;

    Label lblMyCenter;
    Label lblInfo;


    Label lblNoCenter;

    Label lblVaccine;


    Label lbl_continue;
    CenterTools ct;

    Label lblcenterAddress1;
    Label lblcenterAddress2;
    Label lblpostCodeCity;
    Label lblclosingDate;

    Label lblManagedBy;

    TextButton tbPlatformLink;

    float w;
    float h;
    float stateTime;
    JeVeuxMonVaccin app;
    boolean translated = false;
    private ImageButton btnBackMenu;
    private ImageButton btnNext;
    private ImageButton btnliste;
    private ImageButton btnWebsiteD;
    private Texture imageWebsiteD;
    private ImageButton btnWebsiteM;
    private Texture imageWebsiteM;
    private ImageButton btnWebsiteK;
    private Texture imageWebsiteK;
    private OrthographicCamera camera;
    private FitViewport viewport;
    private SpriteBatch sb;

    private boolean reloadCenter = false;
    private int currdept = 0;


    public CenterScreen(JeVeuxMonVaccin app) {
        this.app = app;


    }

    @Override
    public void dispose() {
        sb.dispose();
        stage.dispose();


    }

    public void initResources() {
        if (app.getCenterTools() == null) {
            app.setCenterTools(new CenterTools(app.getMachine(), app));
            ct = app.getCenterTools();
        }
        if (app.getOptions().getDepartment() != Options.UNDEFINED && app.getOptions().getDepartment() != currdept) {
            ct.loadCenter(app.getOptions().getDepartment());
            setCurrdept(app.getOptions().getDepartment());
        }
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

//        SelectBox.SelectBoxStyle boxStyle = departmentBox.getStyle();
//        List.ListStyle ls = boxStyle.listStyle;
//        ScrollPane.ScrollPaneStyle sps = boxStyle.scrollStyle;
//        ScrollPane sp = departmentBox.getScrollPane();
//        sp.setWidth(500);
//        ls.selection.setMinWidth(180);
//        ls.selection.setTopHeight(5);
//        ls.selection.setBottomHeight(2);
//
//        ls.selection.setLeftWidth(5);
//        ls.selection.setRightWidth(5);
//        boxStyle.listStyle = ls;
//        departmentBox.setStyle(boxStyle);

        lblTitle = new Label("Centre de Vaccination", skin);
        lbl_continue = new Label("Poursuivre", skin);

        lblInfo = new Label("Remarque : \nJe vérifie que je suis bien autorisé à recevoir ce vaccin\n" +
                "Je demande l'aide de mon médecin si ma situation est particulière.", skin);


        Label.LabelStyle lblStyleTitle = new Label.LabelStyle();
        lblStyleTitle.fontColor = app.getGraphicTools().getBluetext();
        lblStyleTitle.font = this.skin.getFont("bar-font");
        lblTitle.setStyle(lblStyleTitle);
        lblTitle.setAlignment(Align.center);

        Label.LabelStyle lblStyleIntitule = new Label.LabelStyle();
        lblStyleIntitule.fontColor = app.getGraphicTools().getBluetext();
        lblStyleIntitule.font = this.skin.getFont("menu");

        Label.LabelStyle lblStyleDetail = new Label.LabelStyle();
        lblStyleDetail.fontColor = app.getGraphicTools().getBluetext();
        lblStyleDetail.font = this.skin.getFont("explications");

        Label.LabelStyle lblStyleContinue = new Label.LabelStyle();
        lblStyleContinue.fontColor = Color.BLACK;
        lblStyleContinue.font = this.skin.getFont("smallfont");


        tbPlatformLink = new TextButton("Prendre\nrendez-vous", skin, "link");


        lblMyCenter = new Label("Centre de Vaccination :", skin);

        lblNoCenter = new Label("Aucun centre trouvé par l'application dans ce cas.\n " +
                "____________________________________________________\n\n" +
                "Veuillez essayer sur sante.fr ou modifier votre demande.", skin);
        lblNoCenter.setStyle(lblStyleDetail);

        lblManagedBy = new Label("", skin);
        lblManagedBy.setColor(app.getGraphicTools().getBluetext());
        lblManagedBy.setStyle(lblStyleDetail);

        lblcenterAddress1 = new Label("", skin);
        lblcenterAddress2 = new Label("", skin);
        lblpostCodeCity = new Label("", skin);
        lblclosingDate = new Label("", skin);

        lblVaccine = new Label("xxx", skin);
        lblVaccine.setStyle(lblStyleDetail);
        lblVaccine.setColor(skin.getColor("blue_vaccine"));
        lblNoInternet = new Label("Erreur - problème de connexion internet? ", skin);

        Label.LabelStyle lblStyleError = new Label.LabelStyle();
        lblStyleError.fontColor = Color.ORANGE;
        lblStyleError.font = this.skin.getFont("explications");
        lblNoInternet.setStyle(lblStyleError);
        lblNoInternet.setAlignment(Align.center);
        lblNoInternet.setPosition(w / 2, h - 250, Align.center);


        lblcenterAddress1.setStyle(lblStyleDetail);
        lblcenterAddress2.setStyle(lblStyleDetail);
        lblpostCodeCity.setStyle(lblStyleDetail);
        lblclosingDate.setStyle(lblStyleDetail);
        lblclosingDate.setColor(Color.ORANGE);


        lblMyCenter.setStyle(lblStyleIntitule);
        lblMyCenter.setAlignment(Align.center);

        lblcenterAddress1.setAlignment(Align.left);
        lblcenterAddress2.setAlignment(Align.left);

        Label.LabelStyle lblStyleInfo = new Label.LabelStyle();
        lblStyleInfo.fontColor = app.getGraphicTools().getBluetext();
        lblStyleInfo.font = this.skin.getFont("explications");
        lblInfo.setStyle(lblStyleInfo);

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


        ImageButton.ImageButtonStyle btnStylelist = new ImageButton.ImageButtonStyle();
        btnStylelist.up = this.skin.getDrawable("list-icon");
        this.btnliste = new ImageButton(btnStylelist);

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

        btnliste.setSize(96, 96);
        btnliste.setPosition(w / 2 - btnliste.getWidth() / 2, 4 * h / 5 - 100);
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
                app.getOptions().saveOptions();
                app.setScreen(app.prepareScreen);
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
                app.getOptions().saveOptions();
                app.setScreen(app.prepareScreen);

                return true;

            }
        });


//        departmentBox.setAlignment(Align.center);
//        departmentBox.setPosition(w / 2 - 35, h / 2 + 320);
//        departmentBox.setSize(355, 40);
//        departmentBox.addListener(
//                new ChangeListener() {
//                    @Override
//                    public void changed(ChangeEvent event, Actor actor) {
//                        Department newValue = (Department) departmentBox.getSelected();
//                        deptValueChanged(newValue);
//                        //displayElements();
//                    }
//                }
//
//
//        );
        centerBox = new SelectBox<VaccinationCenter>(skin, "default");
        centerBox.setAlignment(Align.center);
        centerBox.setSize(500, 40);
        centerBox.setPosition(w / 2 - centerBox.getWidth() / 2, h / 2 + 150);

        centerBox.addListener(
                new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent event, Actor actor) {

                        centerValueChanged(centerBox.getSelected());
                        //  displayElements();
                    }
                }
        );


        lblMyCenter.setPosition(w / 7, h / 2 + 200);
        lblMyCenter.setAlignment(Align.left);


//        lblDepartmentSelected.setPosition(w / 2 - lblDepartmentSelected.getWidth() / 2, h / 2 + 225);

        lblTitle.setPosition(w / 2, h - 150, Align.center);
        lblVaccine.setPosition(2 * w / 3, h - 180, Align.left);
        lblInfo.setPosition(20, h / 2 - 190);


        //tbPlatformLink.setSize(330, 96);

        lblNoCenter.setPosition(10, h / 2 + 100);

        lblcenterAddress1.setPosition(w / 6, h / 2 + 100);
        lblcenterAddress2.setPosition(w / 6, h / 2 + 70);
        lblpostCodeCity.setPosition(w / 6, h / 2 + 40);
        lblclosingDate.setPosition(w / 5, h / 2 + 10);

        lblManagedBy.setPosition(40, h / 2 - 60);
        btnWebsiteD.setPosition(w / 4 - btnWebsiteD.getWidth() / 2, h / 2 - 170);
        btnWebsiteK.setPosition(w / 4 - btnWebsiteK.getWidth() / 2, h / 2 - 170);
        btnWebsiteM.setPosition(w / 4 - btnWebsiteM.getWidth() / 2, h / 2 - 170);


        tbPlatformLink.setPosition(3 * w / 4 - tbPlatformLink.getWidth() / 2, hmiddle - 180);

        btnNext.setPosition(w / 2 - btnNext.getWidth() / 2, hmiddle - 440);
        lbl_continue.setPosition(w / 2 - lbl_continue.getWidth() / 2, hmiddle - 480);


        stage.setViewport(viewport);
        stage.addActor(btnBackMenu);


        stage.addActor(lblTitle);


        stage.addActor(lblcenterAddress1);
        stage.addActor(lblcenterAddress2);
        stage.addActor(lblpostCodeCity);
        stage.addActor(lblclosingDate);

        stage.addActor(lblVaccine);
        //    stage.addActor(departmentBox);
        if (ct == null) {
            ct = app.getCenterTools();
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

        if (ct.getCenterStatus() == CenterTools.LOADED) {
            displayElements();
            ct.setCenterStatus(CenterTools.NO_LOAD);
            if (stage.getActors().contains(lblNoInternet, true)) {
                lblNoInternet.remove();
            }

        }
        if (ct.getCenterStatus() == CenterTools.ERROR_LOADING) {
            if (!stage.getActors().contains(lblNoInternet, true)) {
                stage.addActor(lblNoInternet);
            }
        }

        stage.act(delta);
        stage.draw();
    }


    public void showElements() {
        if (reloadCenter) {
            emptyCenterBox();
            lblcenterAddress1.setText("");
            lblcenterAddress2.setText("");

            if (app.getOptions().getDepartment() != Options.UNDEFINED || currdept != app.getOptions().getDepartment()) {
                ct.loadCenter(app.getOptions().getDepartment());
                currdept = app.getOptions().getDepartment();
            }
            if (stage.getActors().contains(lblMyCenter, true)) {
                lblMyCenter.remove();
            }
            if (stage.getActors().contains(centerBox, true)) {
                centerBox.remove();
            }
            if (stage.getActors().contains(lblNoCenter, true)) {
                lblNoCenter.remove();
            }
            setReloadCenter(false);
        }
        if (ct.getCenterStatus() == CenterTools.LOADED) {
            if (fillCenterBox(app.getOptions().getCenterId(), app.getOptions().getVaccineId())) {
                if (!stage.getActors().contains(lblMyCenter, true)) {
                    stage.addActor(lblMyCenter);
                }
                if (!stage.getActors().contains(btnliste, true)) {
                    stage.addActor(btnliste);
                }

                if (!stage.getActors().contains(centerBox, true)) {
                    stage.addActor(centerBox);

                    centerBox.setSelected(app.getOptions().getCenterSelected());
                }
                if (stage.getActors().contains(centerBox, true)) {
                    lblNoCenter.remove();
                }
            } else {
                if (!stage.getActors().contains(lblNoCenter, true)) {
                    stage.addActor(lblNoCenter);
                }
            }
        }

    }


    public void displayElements() {
//        System.out.println(" ----- call to displayElements");
        if (app.getOptions().getDepartment() == Options.UNDEFINED) {
            if (stage.getActors().contains(lblMyCenter, true)) {
                lblMyCenter.remove();
            }
            if (stage.getActors().contains(centerBox, true)) {
                centerBox.remove();
            }
            if (stage.getActors().contains(lblNoCenter, true)) {
                lblNoCenter.remove();
            }
            if (stage.getActors().contains(btnliste, true)) {
                btnliste.remove();
            }

        } else {
            if (fillCenterBox()) {
                if (!stage.getActors().contains(lblMyCenter, true)) {
                    stage.addActor(lblMyCenter);
                }
                if (!stage.getActors().contains(btnliste, true)) {
                    stage.addActor(btnliste);
                }
                if (!stage.getActors().contains(centerBox, true)) {
                    stage.addActor(centerBox);
                }
                if (stage.getActors().contains(lblNoCenter, true)) {
                    lblNoCenter.remove();
                }
            }

        }
    }

    private boolean availableLink() {
        if (centerBox != null && centerBox.getSelected() != null && centerBox.getSelected().getLink() != null) {
            return true;
        }
        return false;
    }


    private void goCenterLink() {
        if (availableLink()) {
            Gdx.net.openURI(centerBox.getSelected().getLink().trim());
        }
    }

    public void emptyCenterBox() {

        if (centerBox.getItems().size > 0) {
            centerBox.clearItems();
        }
        centerValueChanged(VaccinationCenter.getFirstInstance());

    }

    private boolean fillCenterBox() {
        return fillCenterBox(0, 0);
    }

    private boolean fillCenterBox(int centerid, int vaccineid) {
        if (ct.listCenter != null && ct.listCenter.size() > 0 && ct.getCenterStatus() == CenterTools.LOADED) {

            if (centerBox.getItems().size > 0) {
                centerBox.clearItems();
            }


            VaccinationCenter[] vc = new VaccinationCenter[ct.listCenter.size() + 1];
            if (ct.listCenter.size() == 0) {

                centerValueChanged(VaccinationCenter.getFirstInstance());

                return false;
            }
            vc[0] = VaccinationCenter.getFirstInstance();
            int idx = 1;
            Iterator<VaccinationCenter> it = ct.listCenter.iterator();
            VaccinationCenter vcselected = null;
            while (it.hasNext()) {
                VaccinationCenter vcx = it.next();
                vc[idx++] = vcx;
                if (centerid != 0 && vaccineid != 0) {
                    if (centerid == vcx.getCenterId() && vaccineid == vcx.getVaccineId()) {
                        vcselected = vcx;
                    }
                }
            }
            centerBox.setItems(vc);
            if (vcselected != null) {
                centerBox.setSelected(vcselected);
            } else {

                centerBox.setSelected(VaccinationCenter.getFirstInstance());
            }

            ct.setCenterStatus(CenterTools.NO_LOAD);
            return true;
        }
//        System.out.println(" not good selected  list center " + ct.listCenter + " status : " + ct.getCenterStatus());
        return false;

    }


    public void centerValueChanged(VaccinationCenter newCenter) {

        app.getOptions().setCenterSelected(null);

        if (newCenter == null) {
            //   System.out.println(" nul po grave on verra plus tard");
            return;
        }

        if (newCenter.getCenterId() == 0) {
            //     System.out.println(" un reset cdes value");
            if (app.getOptions().getCenterId() == 0) {
                app.getOptions().setSubscriptionPageSeen(false);
                app.getOptions().setControlPageSeen(false);
            }
            lblcenterAddress1.setText("");
            lblcenterAddress2.setText("");
            lblpostCodeCity.setText("");
            lblclosingDate.setText("");
            lblManagedBy.setText("");
            if (stage.getActors().contains(btnWebsiteD, true)) {
                btnWebsiteD.remove();
            }
            if (stage.getActors().contains(btnWebsiteK, true)) {
                btnWebsiteK.remove();
            }
            if (stage.getActors().contains(btnWebsiteM, true)) {
                btnWebsiteM.remove();
            }

            if (stage.getActors().contains(btnNext, true)) {
                btnNext.remove();
            }

            if (stage.getActors().contains(tbPlatformLink, true)) {
                tbPlatformLink.remove();
            }

            if (stage.getActors().contains(lbl_continue, true)) {
                lbl_continue.remove();
            }

            return;
        }
        if (app.getOptions().getCenterId() != newCenter.getCenterId()) {
            app.getOptions().setControlPageSeen(false);
            if (app.getOptions().getCenterId() != 0) {
                app.getOptions().setSubscriptionPageSeen(false);
                app.stopAlert();
                app.getOptions().setAlert(false);
                app.getOptions().saveOptions();
            }

        }
        app.getOptions().setCenterSelected(newCenter);
        app.getOptions().setCenterId(newCenter.getCenterId());
        app.getOptions().setVaccineId(newCenter.getVaccineId());


        if (newCenter.getAddress().length() > 50) {
            lblcenterAddress1.setText(getLinebreaks(newCenter.getAddress(), 50, 0));
            lblcenterAddress2.setText(getLinebreaks(newCenter.getAddress(), 50, 1) + "...");
        } else {
            lblcenterAddress1.setText(newCenter.getAddress());
            lblcenterAddress2.setText("");
        }
        lblpostCodeCity.setText(newCenter.getPostalcode() + " " + newCenter.getCity());
        if (newCenter.getClose_date() != null && !"".equals(newCenter.getClose_date())) {
            lblclosingDate.setText("Ce centre fermera le : " + newCenter.getClose_date());
        }
        int platformid = newCenter.getProviderId();
        if (VaccinationCenter.MAIIA_ID == platformid) {
            if (stage.getActors().contains(btnWebsiteD, true)) {
                btnWebsiteD.remove();
            }
            if (stage.getActors().contains(btnWebsiteK, true)) {
                btnWebsiteK.remove();
            }
            if (!stage.getActors().contains(btnWebsiteM, true)) {
                stage.addActor(btnWebsiteM);
            }
        }

        if (VaccinationCenter.KELDOC_ID == platformid) {
            if (stage.getActors().contains(btnWebsiteD, true)) {
                btnWebsiteD.remove();
            }
            if (stage.getActors().contains(btnWebsiteM, true)) {
                btnWebsiteM.remove();
            }
            if (!stage.getActors().contains(btnWebsiteK, true)) {
                stage.addActor(btnWebsiteK);
            }
        }

        if (VaccinationCenter.DOCTOLIB_ID == platformid) {
            if (stage.getActors().contains(btnWebsiteK, true)) {
                btnWebsiteK.remove();
            }
            if (stage.getActors().contains(btnWebsiteM, true)) {
                btnWebsiteM.remove();
            }
            if (!stage.getActors().contains(btnWebsiteD, true)) {
                stage.addActor(btnWebsiteD);
            }
        }

        if (!stage.getActors().contains(lblManagedBy, true)) {
            stage.addActor(lblManagedBy);
        }
        lblManagedBy.setText("Réservation proposée par :");

        if (!stage.getActors().contains(lbl_continue, true)) {
            stage.addActor(lbl_continue);
        }


        if (availableLink()) {

            if (!stage.getActors().contains(tbPlatformLink, true)) {
                stage.addActor(tbPlatformLink);
            }
        }


        if (!stage.getActors().contains(btnNext, true)) {
            stage.addActor(btnNext);
        }

    }

    public String getLinebreaks(String input, int maxLineLength, int idx) {
        StringTokenizer tok = new StringTokenizer(input, " ");
        StringBuilder output = new StringBuilder(input.length());
        int lineLen = 0;
        int i = 0;
        while (tok.hasMoreTokens()) {
            String word = tok.nextToken();

            if (lineLen + word.length() > maxLineLength) {
                i = i + 1;
                if (i == idx + 1) {
                    return output.toString();
                }
                lineLen = 0;
            }
            if (i == idx) {
                output.append(word + " ");
            }
            lineLen += word.length();
        }
        return output.toString();
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

        if (app.getOptions().getVaccineId() != Options.UNDEFINED) {
            if (app.getOptions().getVaccineId() == Options.PFIZER) {
                lblVaccine.setText("[ARN-m]");
            }
        } else {
            lblVaccine.setText("[Aucun Vaccin - Erreur]");
        }
        Gdx.input.setInputProcessor(stage);
        if (ct.listCenter != null) {
            if (currdept != app.getOptions().getDepartment()) {
                initResources();
            } else {
                ct.setCenterStatus(CenterTools.LOADED);
                showElements();
                ct.selectCenter(app.getOptions().getCenterId(), app.getOptions().getVaccineId());
            }

        } else {
            initResources();
        }

        registerPhone();

    }


    public void registerPhone() {
        if (app.getMachine() == null) {
            System.out.println("houston pb no salt");
            return;
        }
        if (!app.getOptions().isPhoneRegistered()) {
            Timer validTimer = new Timer();

            validTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    try {
                        String urlParameters;
                        urlParameters = "phonesalt=" + URLEncoder.encode(app.getMachine().getSalt(), "UTF-8");
                        //System.out.println(" calling  center : " + PHONE_REGISTER + "?" + urlParameters);
                        String res = executePost(PHONE_REGISTER, urlParameters);
                        registration(res);
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }

                }
            }, 5);
        }


    }

    public void registration(String res) {

        if (res != null) {
            if ("{1}".equals(res)) {
                app.getOptions().setPhoneRegistered(true);
                app.getOptions().saveOptions();
                return;
            }
            if ("{0}".equals(res)) {
                if (!app.getOptions().isPhoneRegistered()) {
                    System.out.println("pb phone registered but unrecognized - it should. say yes");
                    app.getOptions().setPhoneRegistered(true);
                    app.getOptions().saveOptions();

                }
                return;
            }
        }
        if (app.getOptions().isPhoneRegistered()) {
            System.out.println(" abnormal situation -> registration is false ");
            app.getOptions().setPhoneRegistered(false);
            app.getOptions().saveOptions();

        }

        System.out.println("registration failed");

    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }

    public String executePost(String targetURL, String urlParameters) {
        HttpURLConnection connection = null;
        try {
            // Create connection
            URL url = new URL(targetURL);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            connection.setRequestProperty("Content-Length", Integer.toString(urlParameters.getBytes().length));
//            connection.setRequestProperty("Content-Language", "en-US");

            connection.setUseCaches(false);
            connection.setDoOutput(true);

            // Send request
            DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
            wr.writeBytes(urlParameters);
            wr.close();

            // Get Response
            InputStream is = connection.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            StringBuilder response = new StringBuilder(); // or StringBuffer if
            // not Java 5+
            String line;
            while ((line = rd.readLine()) != null) {
                response.append(line);
            }
            rd.close();
            return response.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }

    }


    public boolean isReloadCenter() {
        return reloadCenter;
    }

    public void setReloadCenter(boolean reloadCenter) {
        this.reloadCenter = reloadCenter;
    }

    public int getCurrdept() {
        return currdept;
    }

    public void setCurrdept(int currdept) {
        this.currdept = currdept;
    }
}
