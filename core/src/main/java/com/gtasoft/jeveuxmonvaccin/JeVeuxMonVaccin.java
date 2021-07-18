package com.gtasoft.jeveuxmonvaccin;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.gtasoft.jeveuxmonvaccin.alert.AlertScreen;
import com.gtasoft.jeveuxmonvaccin.center.CenterListScreen;
import com.gtasoft.jeveuxmonvaccin.center.CenterScreen;
import com.gtasoft.jeveuxmonvaccin.center.CenterTools;
import com.gtasoft.jeveuxmonvaccin.control.ControlScreen;
import com.gtasoft.jeveuxmonvaccin.help.HelpScreen;
import com.gtasoft.jeveuxmonvaccin.help.SplashScreen;
import com.gtasoft.jeveuxmonvaccin.menu.MenuScreen;
import com.gtasoft.jeveuxmonvaccin.preparation.PrepareScreen;
import com.gtasoft.jeveuxmonvaccin.resource.GraphicTools;
import com.gtasoft.jeveuxmonvaccin.resource.NativePlatform;
import com.gtasoft.jeveuxmonvaccin.resource.WebClient;
import com.gtasoft.jeveuxmonvaccin.selection.VaccineScreen;
import com.gtasoft.jeveuxmonvaccin.setup.Machine;
import com.gtasoft.jeveuxmonvaccin.setup.Options;

/**
 * {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms.
 */
public class JeVeuxMonVaccin extends Game implements ApplicationListener {
    public HelpScreen helpScreen;
    public VaccineScreen selectScreen;
    public CenterScreen centerScreen;
    public CenterListScreen centerListScreen;
    public ControlScreen controlScreen;

    public AlertScreen alertScreen;
    public PrepareScreen prepareScreen;
    public MenuScreen mainMenuScreen;
    public SplashScreen splashScreen;
    private GraphicTools graphicTools;
    private Options options;
    private String version;
    private NativePlatform np = null;
    private boolean connected = false;
    private WebClient wc;
    private CenterTools centerTools = null;

    public JeVeuxMonVaccin(NativePlatform np) {
        this.np = np;

    }

    public Machine getMachine() {
        if (wc != null) {
            return wc.getMachine();
        }
        return null;
    }

    public void initAlert(int centerid, int vaccineID, String salt, String centerName, int dept) {
        if (np != null) {
            np.initAlert(centerid, vaccineID, salt, centerName, dept);
        }

    }

    public void stopAlert() {
        if (np != null) {
            np.stopAlert();
        }

    }

    public String infoAlert() {
        if (np != null) {
            return np.infoAlert();
        }
        return null;

    }


    public void resourcesInit() {
        mainMenuScreen = new MenuScreen(this);
        selectScreen = new VaccineScreen(this);
        centerScreen = new CenterScreen(this);
        centerListScreen = new CenterListScreen(this);
        prepareScreen = new PrepareScreen(this);
        controlScreen = new ControlScreen(this);
        alertScreen = new AlertScreen(this);
        helpScreen = new HelpScreen(this);

    }

    @Override
    public void create() {
        loadOptions();
        wc = new WebClient(this);
        graphicTools = new GraphicTools();
        splashScreen = new SplashScreen(this);
        setScreen(splashScreen);
    }

    public void loadOptions() {
        if (options == null) {
            options = new Options(isAndroid());
        }

    }


    @Override
    public void dispose() {
        super.dispose();
        graphicTools.dispose();
        Gdx.app.exit();
        System.exit(0);
    }

    public GraphicTools getGraphicTools() {
        return graphicTools;
    }

    public void setGraphicTools(GraphicTools graphicTools) {
        this.graphicTools = graphicTools;
    }

    public Options getOptions() {
        return options;
    }

    public void setOptions(Options options) {
        this.options = options;
    }

    public boolean isAndroid() {

        return np.isAndroid();
    }

    public void setConnected(boolean b) {
        connected = b;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public CenterTools getCenterTools() {
        return centerTools;
    }

    public void setCenterTools(CenterTools centerTools) {
        this.centerTools = centerTools;
    }
}