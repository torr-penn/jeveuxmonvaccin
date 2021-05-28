package com.gtasoft.jeveuxmonvaccin.resource;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Align;

public class GraphicTools {
    static public String LOADING_MSG = "Chargement";//loading message in local language
    Label labelLoad;
    Label.LabelStyle lblStyle;
    String msgLoading;
    long tx = 0;
    private GraphicTools _instance = null;
    private Skin skin;
    private Texture imgAlert;
    private Texture imgBackground;
    private Texture imgBack;
    private Texture imgBackFr;

    private Texture imgExit;
    private Texture imgHelp;
    private Texture imgLoading;
    private Texture imgMyself;
    private Texture imgNext;
    private Texture imgRegistration;
    private Texture imgRestart;
    private Texture imgReloadOff;
    private Texture imgSearch;
    private Texture imgSelectPlace;
    private Texture imgSettings;
    private Texture imgStart;
    private Texture imgOk;
    private Texture imgKo;
    private Texture imgBon;


    public GraphicTools() {
        lblStyle = new Label.LabelStyle();
        setSkin(new Skin(Gdx.files.internal("ui/jeveuxmonvaccin.json")));
        lblStyle.font = this.getSkin().getFont("typewriter");
        lblStyle.font.getData().setScale(1.5f);
        lblStyle.fontColor = Color.ORANGE;


        imgAlert = new Texture(Gdx.files.internal("img/menu/alert-icon.png"));
        skin.add("imgAlert", imgAlert);

        this.imgBack = new Texture(Gdx.files.internal("img/menu/back-icon-medium.png"));
        skin.add("imgBack", imgBack);

        this.imgBackFr = new Texture(Gdx.files.internal("img/menu/back-icon.png"));
        skin.add("imgBackFr", imgBackFr);

        imgExit = new Texture(Gdx.files.internal("img/menu/cross-icon-small.png"));
        skin.add("imgExit", imgExit);

        imgHelp = new Texture(Gdx.files.internal("img/menu/help-icon-small.png"));
        skin.add("imgHelp", imgHelp);

        imgLoading = new Texture(Gdx.files.internal("img/menu/loading-icon.png"));
        skin.add("imgLoading", imgLoading);

        imgMyself = new Texture(Gdx.files.internal("img/menu/myself-icon.png"));
        skin.add("imgMyself", imgMyself);

        imgNext = new Texture(Gdx.files.internal("img/menu/next-icon.png"));
        skin.add("imgNext", imgNext);

        imgRegistration = new Texture(Gdx.files.internal("img/menu/registration-icon.png"));
        skin.add("imgRegistration", imgRegistration);

        imgRestart = new Texture(Gdx.files.internal("img/menu/retry-icon.png"));
        skin.add("imgRestart", imgRestart);

        setImgReloadOff(new Texture(Gdx.files.internal("img/menu/retry-icon-off.png")));
        skin.add("imgReloadOff", imgReloadOff);
        Texture imageListe = new Texture(Gdx.files.internal("img/menu/list-icon.png"));
        this.skin.add("list-icon", imageListe);

        imgSearch = new Texture(Gdx.files.internal("img/menu/search-icon.png"));
        skin.add("imgSearch", imgSearch);

        imgSelectPlace = new Texture(Gdx.files.internal("img/menu/select-place-icon.png"));
        skin.add("imgSelectPlace", imgSelectPlace);

        imgSettings = new Texture(Gdx.files.internal("img/menu/settings-icon.png"));
        skin.add("imgSettings", imgSettings);

        imgStart = new Texture(Gdx.files.internal("img/menu/start-icon.png"));
        skin.add("imgStart", imgStart);

        imgOk = new Texture(Gdx.files.internal("img/selection/ok.png"));
        skin.add("imgOk", imgOk);
        imgKo = new Texture(Gdx.files.internal("img/selection/ko.png"));
        skin.add("imgKo", imgKo);
        imgBon = new Texture(Gdx.files.internal("img/selection/bon.png"));
        skin.add("imgBon", imgBon);


        msgLoading = "Chargement";
        labelLoad = new Label(msgLoading, getSkin());
        labelLoad.setStyle(lblStyle);
        labelLoad.setName(LOADING_MSG);
        labelLoad.setAlignment(Align.left);
        //labelLoad.setSize(200, 40);
        imgBackground = new Texture(Gdx.files.internal("img/menu/background.png"));
        getImgBackground().setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
    }


    public void isLoadingImg(SpriteBatch sb, int posx, int posy) {
        if (sb != null) {
            sb.draw(this.imgLoading, posx, posy);
        }
    }

    public void isLoadingText(boolean visible, Stage stage, int posx, int posy) {
        if (visible) {
            for (Actor ac : stage.getActors()) {
                if (LOADING_MSG.equals(ac.getName())) {
                    Label lb1 = (Label) ac;
                    lb1.setText(msgLoading);
                    break;
                }
            }
        }
        isLoadingTextWithAnimation(visible, stage, posx, posy, false);
    }

    public String randomSpace(String str) {

        if (str != null) {
            int l = str.length();
            if (l > 1) {
                int x = (int) Math.round((l - 1) * Math.random()) + 1;
                if (x < l - 1) {
                    return str.substring(0, x) + " " + str.substring(x + 1, l);
                } else {
                    return str.substring(0, x) + " ";
                }

            }

        }
        return ".";
    }

    public void isLoadingTextWithAnimation(boolean visible, Stage stage, int posx, int posy, boolean animated) {

        if (animated) {
            long t1 = System.currentTimeMillis();
            if (t1 > tx) {
                tx = t1 + 400;
                if (visible) {
                    for (Actor ac : stage.getActors()) {
                        if (LOADING_MSG.equals(ac.getName())) {
                            Label lb1 = (Label) ac;
                            lb1.setText(randomSpace(msgLoading));
                            break;
                        }
                    }

                }
            }
        }
        boolean found = false;

        labelLoad.setPosition(posx, posy);

        if (visible) {

            for (Actor ac : stage.getActors()) {
                if (LOADING_MSG.equals(ac.getName())) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                stage.addActor(labelLoad);
            }


        } else {
            for (Actor ac : stage.getActors()) {
                if (LOADING_MSG.equals(ac.getName())) {
                    ac.remove();
                    break;
                }
            }
        }

    }

    public Skin getSkin() {
        return skin;
    }

    public void setSkin(Skin skin) {
        this.skin = skin;
    }

    public void dispose() {
        imgExit.dispose();
        imgBackground.dispose();
        imgSettings.dispose();
        imgAlert.dispose();
        imgHelp.dispose();
        imgBack.dispose();
        imgLoading.dispose();
        imgMyself.dispose();
        imgRestart.dispose();
        imgNext.dispose();
        imgRegistration.dispose();
        imgSearch.dispose();
        imgSelectPlace.dispose();
        imgStart.dispose();
    }

    public Texture getImgBackground() {
        return imgBackground;
    }

    public Texture getImgBack() {
        return imgBack;
    }

    public Texture getImgExit() {
        return imgExit;
    }

    public Texture getImgHelp() {
        return imgHelp;
    }

    public Texture getImgMyself() {
        return imgNext;
    }

    public Texture getImgNext() {
        return imgNext;
    }

    public Texture getImgRestart() {
        return imgRestart;
    }

    public Texture getImgStart() {
        return imgStart;
    }

    public Texture getImgSettings() {
        return imgSettings;
    }


    public Texture getImgReloadOff() {
        return imgReloadOff;
    }

    public void setImgReloadOff(Texture imgReloadOff) {
        this.imgReloadOff = imgReloadOff;
    }
}
