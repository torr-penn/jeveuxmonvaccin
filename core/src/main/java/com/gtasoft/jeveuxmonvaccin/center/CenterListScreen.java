package com.gtasoft.jeveuxmonvaccin.center;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.gtasoft.jeveuxmonvaccin.JeVeuxMonVaccin;
import com.gtasoft.jeveuxmonvaccin.setup.Options;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;

public class CenterListScreen implements Screen, ApplicationListener {

    private static String PHONE_REGISTER = "http://www.torr-penn.com/jeveuxmonvaccin/phoneRegister";
    private static int WAITINGTIME = 5;
    Stage stage;
    Skin skin;
    Label lblTitle;
    Label lblNoInternet;
    Label lblTimer;

    CenterTools ct;
    float w;
    float h;
    float stateTime;
    JeVeuxMonVaccin app;
    boolean translated = false;
    Table fullTable = null;
    Table titleTable = null;
    ScrollPane scroller = null;
    Table tableleft = null;
    long ltimer = 0;
    private ImageButton btnBackMenu;
    private ImageButton btnReload;
    private ImageButton btnReloadOff;
    private OrthographicCamera camera;
    private FitViewport viewport;
    private SpriteBatch sb;
    private boolean reloadCenter = false;
    private int dep = 0;

    public CenterListScreen(JeVeuxMonVaccin app) {
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
        if (app.getOptions().getDepartment() != Options.UNDEFINED) {
            dep = app.getOptions().getDepartment();
            ct.loadFullCenter(dep);

        }
    }

    @Override
    public void create() {
        w = 768;
        h = 1280;
        stateTime = 0f;
        if (ct == null) {
            ct = app.getCenterTools();
            initResources();
        }

        sb = new SpriteBatch();

        skin = app.getGraphicTools().getSkin();

        stage = new Stage();
        camera = new OrthographicCamera();
        viewport = new FitViewport(768, 1280, camera);
        ImageButton.ImageButtonStyle btnStyle = new ImageButton.ImageButtonStyle();
        btnStyle.up = this.skin.getDrawable("imgBackRetour");
        this.btnBackMenu = new ImageButton(btnStyle);

        ImageButton.ImageButtonStyle btnStyleroff = new ImageButton.ImageButtonStyle();
        btnStyleroff.up = this.skin.getDrawable("imgReloadOff");
        this.btnReloadOff = new ImageButton(btnStyleroff);

        ImageButton.ImageButtonStyle btnStyler = new ImageButton.ImageButtonStyle();
        btnStyler.up = this.skin.getDrawable("imgRestart");
        this.btnReload = new ImageButton(btnStyler);

        Label.LabelStyle lblStyleTitle = new Label.LabelStyle();
        lblStyleTitle.fontColor = app.getGraphicTools().getBluetext();
        lblStyleTitle.font = this.skin.getFont("bar-font");
        lblTitle = new Label("Je Veux Mon Vaccin!", skin);
        lblTitle.setStyle(lblStyleTitle);
        lblTitle.setAlignment(Align.center);
        lblTitle.setPosition(w / 2, h - 150, Align.center);

        Label.LabelStyle lblStyleError = new Label.LabelStyle();
        lblStyleError.fontColor = Color.ORANGE;
        lblStyleError.font = this.skin.getFont("explications");
        lblNoInternet = new Label("Erreur - problème de connexion internet? ", skin);
        lblNoInternet.setStyle(lblStyleError);
        lblNoInternet.setAlignment(Align.center);
        lblNoInternet.setPosition(w / 2, h - 25, Align.center);

        Label.LabelStyle lblStyleCountdown = new Label.LabelStyle();
        lblStyleCountdown.fontColor = app.getGraphicTools().getBluetext();
        lblStyleCountdown.font = this.skin.getFont("listBold");
        lblTimer = new Label("", skin);
        lblTimer.setStyle(lblStyleCountdown);


        btnBackMenu.setSize(64, 64);
        btnBackMenu.setPosition(w - 24 - 64, h - 24 - 64);
        this.btnBackMenu.addListener(new ClickListener() {
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
        btnReloadOff.setSize(64, 64);
        btnReloadOff.setPosition(24, h - 24 - 64);
        btnReload.setSize(64, 64);
        btnReload.setPosition(24, h - 24 - 64);
        lblTimer.setPosition(24 + 32, h - 24 - 64 - 24);
        this.btnReload.addListener(new ClickListener() {
            @Override
            public void touchUp(InputEvent e, float x, float y, int point, int button) {


                return;
            }

            @Override
            public boolean touchDown(InputEvent e, float x, float y, int point, int button) {

                initResources();
                return true;

            }
        });

        stage.setViewport(viewport);
        titleTable = new Table(skin);
        scroller = new ScrollPane(titleTable);
        scroller.setForceScroll(false, true);
        scroller.setFadeScrollBars(true);
        scroller.setColor(Color.PURPLE);
        tableleft = new Table();
        //tableleft.setSize(758, 1100);
        //tableleft.setPosition(0, 0);
        //tableleft.setColor(Color.LIGHT_GRAY);
        tableleft.add(scroller);
        stage.addActor(lblTitle);
        stage.addActor(lblTimer);
        //stage.addActor(tableleft);
        stage.addActor(btnBackMenu);
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
        app.getGraphicTools().isLoadingTextWithAnimation((ct.getFullCenterStatus() == CenterTools.LOADING), stage, (int) (w / 2), (int) (h - 220), true);

        if (ct.getFullCenterStatus() == CenterTools.LOADED) {
            ct.setFullCenterStatus(CenterTools.NO_LOAD);
            resetStage();
            ltimer = System.currentTimeMillis();
            if (stage.getActors().contains(btnReload, true)) {
                btnReload.remove();
            }
            if (!stage.getActors().contains(btnReloadOff, true)) {
                stage.addActor(btnReloadOff);
            }
            if (!stage.getActors().contains(lblTimer, true)) {
                stage.addActor(lblTimer);
            }
            if (app.getOptions().getDepartment() == 29) {
                lblTitle.setText("Je Veux Mon Vaccin!\nFinistère");
            }
            if (app.getOptions().getDepartment() == 22) {
                lblTitle.setText("Je Veux Mon Vaccin!\nCôtes-d'Armor");
            }
            if (app.getOptions().getDepartment() == 35) {
                lblTitle.setText("Je Veux Mon Vaccin!\nIlle-et-Vilaine");
            }
            if (app.getOptions().getDepartment() == 56) {
                lblTitle.setText("Je Veux Mon Vaccin!\nMorbihan");
            }
            if (app.getOptions().getDepartment() == 44) {
                lblTitle.setText("Je Veux Mon Vaccin!\nLoire-Atlantique");
            }
            if (app.getOptions().getDepartment() == 49) {
                lblTitle.setText("Je Veux Mon Vaccin!\nMaine-et-Loire");
            }
            if (app.getOptions().getDepartment() == 53) {
                lblTitle.setText("Je Veux Mon Vaccin!\nMayenne");
            }
            if (app.getOptions().getDepartment() == 72) {
                lblTitle.setText("Je Veux Mon Vaccin!\nSarthe");
            }
            if (app.getOptions().getDepartment() == 85) {
                lblTitle.setText("Je Veux Mon Vaccin!\nVendée");
            }
            if (stage.getActors().contains(lblNoInternet, true)) {
                lblNoInternet.remove();
            }

        }
        if (ct.getFullCenterStatus() == CenterTools.ERROR_LOADING) {
            if (!stage.getActors().contains(lblNoInternet, true)) {
                stage.addActor(lblNoInternet);
            }
            if (stage.getActors().contains(lblTimer, true)) {
                lblTimer.remove();
            }
        }

        if (ltimer != 0) {
            long now = System.currentTimeMillis();
            if (ltimer + WAITINGTIME * 1000 < now) {

                if (stage.getActors().contains(btnReloadOff, true)) {
                    btnReloadOff.remove();
                }
                if (!stage.getActors().contains(btnReload, true)) {
                    stage.addActor(btnReload);
                }
                if (stage.getActors().contains(lblTimer, true)) {
                    lblTimer.remove();
                }

            } else {
                lblTimer.setText("" + (WAITINGTIME - (int) ((now - ltimer) / 1000)));
            }
        }
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

        if (sb == null) {
            create();
        }


        Gdx.input.setInputProcessor(stage);
        if (app.getCenterTools() != null) {
            initResources();
        }
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

    public void resetStage() {
        resetStage(0);
    }


    public ArrayList<VaccinationCenterFull> sort(ArrayList<VaccinationCenterFull> list, int sortOrder) {
        if (sortOrder == 1) {
            Collections.sort(list, VaccinationCenterFull.nameComparator);
        }
        if (sortOrder == 0) {
            Collections.sort(list, VaccinationCenterFull.timeComparator);
        }
        if (sortOrder == 2) {
            Collections.sort(list, VaccinationCenterFull.dispoComparator);
        }
        return list;
    }

    //sortOrder 0 - RDV
    // 1 - Name
    //2 %dispo


    public void resetStage(int sortOrder) {
        Image imgLocal = null;
        ArrayList<VaccinationCenterFull> subSelect = sort(app.getCenterTools().getFullList(), sortOrder);

        if (subSelect == null) {
            return;
        }
        // Cell c=stage.getCell

        if (tableleft != null) {
            tableleft.remove();
        }
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM YYYY");
        // drawit(, fontd, 3, 5 + j);
        int nb = subSelect.size();

        titleTable = new Table(skin);
//        titleTable.setBounds(0, 0, Gdx.graphics.getHeight(), Gdx.graphics.getWidth());
        final TextButton buttonpo = new TextButton(" Centre ", skin, "listTitle");
        buttonpo.setSize(380, 30);
        buttonpo.addListener(new ClickListener() {
            @Override
            public void touchUp(InputEvent e, float x, float y, int point, int button) {

                resetStage(1);
                return;
            }

            @Override
            public boolean touchDown(InputEvent e, float x, float y, int point, int button) {


                return true;

            }
        });

        titleTable.add(buttonpo).colspan(2).left().width(380);
        final TextButton buttone = new TextButton("Dernier\nRDV rapide", skin, "listTitleNeutral");
        buttone.setSize(150, 30);
        titleTable.add(buttone).left().width(150);

        final TextButton buttont = new TextButton(" Disponibilité ", skin, "listTitle");
        buttont.setSize(120, 30);
        titleTable.add(buttont).center().width(120);
        buttont.addListener(new ClickListener() {
            @Override
            public void touchUp(InputEvent e, float x, float y, int point, int button) {
                resetStage(2);

                return;
            }

            @Override
            public boolean touchDown(InputEvent e, float x, float y, int point, int button) {


                return true;

            }
        });

        //------------------------------------
        titleTable.row();
        final TextButton buttonp = new TextButton("  RDV  ", skin, "listTitle");
        buttonp.setSize(200, 30);
        buttonp.addListener(new ClickListener() {
            @Override
            public void touchUp(InputEvent e, float x, float y, int point, int button) {

                resetStage(0);
                return;
            }

            @Override
            public boolean touchDown(InputEvent e, float x, float y, int point, int button) {


                return true;

            }
        });
        titleTable.add(buttonp).left().width(200);

        final TextButton buttons = new TextButton(" Réservez ", skin, "listTitle");
        buttons.setSize(180, 30);
        buttons.addListener(new ClickListener() {
            @Override
            public void touchUp(InputEvent e, float x, float y, int point, int button) {
                resetStage(0);

                return;
            }

            @Override
            public boolean touchDown(InputEvent e, float x, float y, int point, int button) {


                return true;

            }
        });
        titleTable.add(buttons).right().width(180);


        final TextButton buttond = new TextButton(" Mis à jour ", skin, "listTitleNeutral");
        buttond.setSize(150, 30);
        titleTable.add(buttond).left().width(150);
        final TextButton buttono = new TextButton(" Plateforme ", skin, "listTitleNeutral");
        buttono.setSize(120, 30);

        titleTable.add(buttono).right().width(120);

//Pixmap with one pixel
        Pixmap pm1 = new Pixmap(1, 1, Pixmap.Format.RGB565);
        pm1.setColor(Color.WHITE);
        pm1.fill();

        Pixmap pm2 = new Pixmap(1, 1, Pixmap.Format.RGB565);
        pm2.setColor(new Color(1.00f, 0.95f, 0.89f, 1f));
        pm2.fill();
        titleTable.row();
        titleTable.setBackground(new TextureRegionDrawable(new TextureRegion(new Texture(pm1))));
        Table myfullTable = new Table(skin);
        //myfullTable.setBounds(0, 0, 750, 1100);

        myfullTable.add(titleTable);
        myfullTable.row();
        int j = 0;
        Color bluetext = app.getGraphicTools().getBluetext();
        Color greentext = skin.getColor("green");
        for (int i = 0; i < nb; i++) {

            VaccinationCenterFull vc = subSelect.get(i);
            if (vc.getZipcode() != 0 && app.getOptions().getDepartment() * 1000 <= vc.getZipcode() && (app.getOptions().getDepartment() + 1) * 1000 > vc.getZipcode()) {
                j++;
                Table tablex = new Table(skin);
                if (j % 2 == 0) {
                    tablex.setBackground(new TextureRegionDrawable(new TextureRegion(new Texture(pm1))));
                } else {
                    tablex.setBackground(new TextureRegionDrawable(new TextureRegion(new Texture(pm2))));
                }
                final Label lp = new Label("  " + printableName(vc.getCity(), vc.getName()), skin, "listBold");
                lp.setColor(bluetext);
                tablex.add(lp).colspan(2).width(380).left();
                final Label ltt = new Label("" + vc.getReadableTime() + "", skin, "list");
                ltt.setColor(bluetext);
                tablex.add(ltt).left().width(150);


                if (vc.getTotal() != 0) {
                    Table tbpct = new Table(skin);
                    int pct = Math.round(((float) vc.getNbsuccess() / (float) vc.getTotal()) * 100f);

                    float pctg = (float) pct * 128f / 25600f;
                    float pctr = (100f - (float) pct) * 128f / 25600f;
                    //  System.out.println(" pctr " + pctr + " pctg :" + pctg);
                    Color col = new Color(pctr, pctg, 0.15f, 1f);

                    Label.LabelStyle lspct = new Label.LabelStyle();
                    lspct.fontColor = Color.WHITE;
                    lspct.font = skin.getFont("listBold");


                    Label ltt1 = new Label("" + pct + " %", lspct);
                    Pixmap labelColor = new Pixmap(120, 40, Pixmap.Format.RGB888);

                    labelColor.setColor(col);
                    labelColor.fill();

                    ltt1.setAlignment(Align.center);
                    tbpct.setBackground(new Image(new Texture(labelColor)).getDrawable());
                    tbpct.add(ltt1).fill().expand();

                    tablex.add(tbpct).width(120).right();
                } else {
                    Label ltt2 = new Label("0 %", skin, "listBold");
                    tablex.add(ltt2).width(120).right();

                    ltt2.setAlignment(Align.center);
                }

                tablex.row();
                // ------------------------------------


                final Label le = new Label("  " + vc.getReadableRdv() + "", skin, "listBold");
                le.setColor(greentext);
                tablex.add(le).left().width(200);

                ImageButton.ImageButtonStyle btnStyle = new ImageButton.ImageButtonStyle();

                if (vc.getStatus() == VaccinationCenterFull.STATUS_OK) {
                    btnStyle.up = this.skin.getDrawable("imgOk");
                } else if ("".equals(vc.getReadableRdv().trim())) {
                    btnStyle.up = this.skin.getDrawable("imgKo");
                } else {
                    btnStyle.up = this.skin.getDrawable("imgBon");
                }

                ImageButton btnRdv = new ImageButton(btnStyle);

                btnRdv.addListener(new ClickListener() {
                    @Override
                    public void touchUp(InputEvent e, float x, float y, int point, int button) {
                        Gdx.net.openURI(vc.getLink().trim());

                        return;
                    }


                    @Override
                    public boolean touchDown(InputEvent e, float x, float y, int point, int button) {


                        return true;

                    }
                });

                //   imgLocal.setSize(45, 45);
                Table tbz = new Table(skin);
                tbz.add(btnRdv).width(45).height(45).center();
                tablex.add(tbz).center().width(180);

                final Label lttlc = new Label("" + vc.getReadableLastcheck() + "", skin, "list");
                lttlc.setColor(bluetext);
                tablex.add(lttlc).left().width(150);


                // add a picture
                if (vc.getPlatformId() == VaccinationCenterFull.DOCTOLIB) {
                    imgLocal = new Image(new Texture(Gdx.files.internal("img/selection/doctolib120x38.png")));
                } else if (vc.getPlatformId() == VaccinationCenterFull.MAIIA) {
                    imgLocal = new Image(new Texture(Gdx.files.internal("img/selection/maiia120x38.png")));
                } else if (vc.getPlatformId() == VaccinationCenterFull.KELDOC) {
                    imgLocal = new Image(new Texture(Gdx.files.internal("img/selection/keldoc120x38.png")));
                }
                Table tbzz = new Table(skin);
                tbzz.add(imgLocal).width(120).height(38).center();
                tablex.add(tbzz).right().width(120).height(40);

                tablex.row();


                myfullTable.add(tablex).expandX();
                myfullTable.row();
            }
        }

        tableleft.removeActor(scroller);
        scroller = new ScrollPane(myfullTable);
        fullTable = myfullTable;
        scroller.setForceScroll(false, true);
        scroller.setFadeScrollBars(false);

        // tableleft.setFillParent(true);


        tableleft.setSize(758, 1050);
        tableleft.setPosition(0, 0);

        tableleft.add(scroller);
        if (!stage.getActors().contains(tableleft, true)) {


            stage.addActor(tableleft);
        }
        // Cell c=stage.getCell(tableleft);

    }

    public String printableName(String city, String name) {
        if (name.length() > 32) {
            return name.substring(0, 31) + "...";
        } else {
            return name;
        }
    }

    public boolean isReloadCenter() {
        return reloadCenter;
    }

    public void setReloadCenter(boolean reloadCenter) {
        this.reloadCenter = reloadCenter;
    }
}
