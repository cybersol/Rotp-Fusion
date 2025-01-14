package rotp.model.game;

import static rotp.model.galaxy.StarSystem.getMinFont;
import static rotp.model.galaxy.StarSystem.setFontPct;
import static rotp.model.galaxy.StarSystem.setMinFont;
import static rotp.model.galaxy.StarSystem.setMinFont2;
import static rotp.model.game.DefaultValues.DEF_VAL;
import static rotp.model.game.DefaultValues.FUSION_DEFAULT;
import static rotp.model.game.DefaultValues.MOO1_DEFAULT;
import static rotp.model.game.DefaultValues.ROTP_DEFAULT;
import static rotp.ui.UserPreferences.backupTurns;
import static rotp.ui.UserPreferences.disableAdvisor;
import static rotp.ui.UserPreferences.displayMode;
import static rotp.ui.UserPreferences.displayModeToSettingName;
import static rotp.ui.UserPreferences.graphicsMode;
import static rotp.ui.UserPreferences.graphicsModeToSettingName;
import static rotp.ui.UserPreferences.musicVolume;
import static rotp.ui.UserPreferences.playMusic;
import static rotp.ui.UserPreferences.playSounds;
import static rotp.ui.UserPreferences.saveDir;
import static rotp.ui.UserPreferences.saveDirectoryPath;
import static rotp.ui.UserPreferences.selectedScreen;
import static rotp.ui.UserPreferences.sensitivityMode;
import static rotp.ui.UserPreferences.sensitivityToSettingName;
import static rotp.ui.UserPreferences.soundVolume;
import static rotp.ui.UserPreferences.texturesMode;
import static rotp.ui.UserPreferences.texturesToSettingName;
import static rotp.ui.main.GalaxyMapPanel.maxFlagScale;
import static rotp.ui.main.GalaxyMapPanel.maxFleetHugeScale;
import static rotp.ui.main.GalaxyMapPanel.maxFleetLargeScale;
import static rotp.ui.main.GalaxyMapPanel.maxFleetSmallScale;
import static rotp.ui.main.GalaxyMapPanel.maxFleetTransportScale;
import static rotp.ui.main.GalaxyMapPanel.maxFleetUnarmedScale;
import static rotp.ui.main.GalaxyMapPanel.maxRallyScale;
import static rotp.ui.main.GalaxyMapPanel.maxStargateScale;
import static rotp.ui.util.IParam.langLabel;

import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.io.File;
import java.util.Arrays;
import java.util.HashMap;

import javax.swing.JFileChooser;

import rotp.Rotp;
import rotp.ui.RotPUI;
import rotp.ui.UserPreferences;
import rotp.ui.game.BaseModPanel;
import rotp.ui.game.GameUI;
import rotp.ui.game.MainOptionsUI;
import rotp.ui.util.ParamBoolInt;
import rotp.ui.util.ParamBoolean;
import rotp.ui.util.ParamFloat;
import rotp.ui.util.ParamInteger;
import rotp.ui.util.ParamList;
import rotp.ui.util.ParamOptions;
import rotp.ui.util.ParamSpeciesName;
import rotp.ui.util.ParamString;
import rotp.ui.util.ParamSubUI;
import rotp.ui.util.ParamTitle;
import rotp.util.FontManager;
import rotp.util.LanguageManager;
import rotp.util.sound.SoundManager;

public interface IMainOptions extends IDebugOptions, ICombatOptions {
	String DEBUG_GUI_ID		    = "DEBUG_OPTIONS";
	String WINDOW_MODE			= "GAME_SETTINGS_WINDOWED";
	String BORDERLESS_MODE		= "GAME_SETTINGS_BORDERLESS";
	String FULLSCREEN_MODE		= "GAME_SETTINGS_FULLSCREEN";
	String GRAPHICS_LOW			= "GAME_SETTINGS_GRAPHICS_LOW";
	String GRAPHICS_MEDIUM		= "GAME_SETTINGS_GRAPHICS_MED";
	String GRAPHICS_HIGH		= "GAME_SETTINGS_GRAPHICS_HIGH";
	String GRAPHICS_RETINA		= "GAME_SETTINGS_GRAPHICS_RETINA";
	String TEXTURES_NO			= "GAME_SETTINGS_TEXTURES_NO";
	String TEXTURES_INTERFACE	= "GAME_SETTINGS_TEXTURES_INTERFACE";
	String TEXTURES_MAP			= "GAME_SETTINGS_TEXTURES_MAP";
	String TEXTURES_BOTH		= "GAME_SETTINGS_TEXTURES_BOTH";
	String SAVEDIR_DEFAULT		= "GAME_SETTINGS_SAVEDIR_DEFAULT";
	String SAVEDIR_CUSTOM		= "GAME_SETTINGS_SAVEDIR_CUSTOM";
	String SENSITIVITY_HIGH		= "GAME_SETTINGS_SENSITIVITY_HIGH";
	String SENSITIVITY_MEDIUM	= "GAME_SETTINGS_SENSITIVITY_MEDIUM";
	String SENSITIVITY_LOW		= "GAME_SETTINGS_SENSITIVITY_LOW";
	int	   MAX_BACKUP_TURNS		= 20; // modnar: change max turns between backups to 20
	
	// ==================== Duplicates for Base Main Settings Options ====================
	//
	ParamList displayMode = new DisplayMode();
	class DisplayMode extends ParamList {
		DisplayMode() {
			super( GAME_UI, "DISPLAY_MODE",
					Arrays.asList(
							WINDOW_MODE,
							BORDERLESS_MODE,
							FULLSCREEN_MODE
							),
					WINDOW_MODE);
			isDuplicate(true);
			isCfgFile(true);
			showFullGuide(true);
		}
		@Override public String getCfgValue()		{ return displayModeToSettingName(get()); }
		@Override public String getOption()			{ return displayMode(); }
		@Override public void setOption(String s)	{ displayMode(s); }
	}
//	default boolean fullScreen()	{ return displayMode().equals(FULLSCREEN_MODE); }
//	default boolean windowed()		{ return displayMode().equals(WINDOW_MODE); }
//	default boolean borderless()	{ return displayMode().equals(BORDERLESS_MODE); }
//	default String displayMode()	{ return displayMode.get(); }
	
	ParamList graphicsMode = new GraphicsMode();
	class GraphicsMode extends ParamList {
		GraphicsMode() {
			super(GAME_UI, "GRAPHICS",
					Arrays.asList(
							GRAPHICS_HIGH,
							GRAPHICS_MEDIUM,
							GRAPHICS_LOW,
							GRAPHICS_RETINA
							),
					GRAPHICS_HIGH);
			isDuplicate(true);
			isCfgFile(true);
			showFullGuide(true);
		}
		@Override public String getCfgValue()		{ return graphicsModeToSettingName(get()); }
		@Override public String getOption()			{ return graphicsMode(); }
		@Override public void setOption(String s)	{ graphicsMode(s); }
	}
//	default boolean playAnimations()	{ return !graphicsMode.get().equals(GRAPHICS_LOW); }
//	default boolean antiAliasing()		{ return graphicsMode.get().equals(GRAPHICS_HIGH); }

	ParamList texturesMode = new TexturesMode();
	class TexturesMode extends ParamList {
		TexturesMode() {
			super(GAME_UI, "TEXTURES",
					Arrays.asList(
							TEXTURES_NO,
							TEXTURES_INTERFACE,
							TEXTURES_MAP,
							TEXTURES_BOTH
							),
					TEXTURES_BOTH);
			isDuplicate(true);
			isCfgFile(true);
			showFullGuide(true);
		}
		@Override public String getCfgValue()		{ return texturesToSettingName(get()); }
		@Override public String getOption()			{ return texturesMode(); }
		@Override public void setOption(String s)	{ texturesMode(s); }
	}
//	default boolean texturesInterface()		{ return texturesInterface(); }
//	default boolean texturesMap()			{ return texturesMap(); }

	ParamList sensitivityMode = new SensitivityMode();
	class SensitivityMode extends ParamList {
		SensitivityMode() {
			super(GAME_UI, "SENSITIVITY",
					Arrays.asList(
							SENSITIVITY_LOW,
							SENSITIVITY_MEDIUM,
							SENSITIVITY_HIGH
							),
					SENSITIVITY_MEDIUM);
			isDuplicate(true);
			isCfgFile(true);
			showFullGuide(true);
		}
		@Override public String getCfgValue()		{ return sensitivityToSettingName(get()); }
		@Override public String getOption()			{ return sensitivityMode(); }
		@Override public void setOption(String s)	{ sensitivityMode(s); }
	}
//	default boolean sensitivityMedium()		{ return sensitivityMedium(); }
//	default boolean sensitivityLow()		{ return sensitivityLow(); }

	// Do no use alone, Go through soundVolume
	ParamBoolean playSounds = new PlaySounds();
	class PlaySounds extends ParamBoolean {
		PlaySounds() {
			super(GAME_UI, "SOUNDS", true);
			isDuplicate(true);
			isCfgFile(true);
		}
		@Override public Boolean getOption()		{ return playSounds(); }
		@Override public void setOption(Boolean b)	{
			if(playSounds() != b)
				//playSounds(!s); // ! because following line will toggle!
				SoundManager.current().toggleSounds();
		}
	}

	ParamBoolInt soundVolume = new SoundVolume();
	class SoundVolume extends ParamBoolInt {
		SoundVolume() {
			super(GAME_UI, playSounds,
					"SOUNDS_ON", "SOUNDS_OFF", 10, 0, 10);
			isDuplicate(true);
			isCfgFile(true);
		}
		@Override public String getCfgLabel()		{ return "SOUND_VOLUME"; }
		@Override public Integer getOption()		{ return soundVolume(); }
		@Override public void setOption(Integer i)	{
			soundVolume(Math.abs(i));
			SoundManager.current().resetSoundVolumes();
		}
	}
//	default int soundVolume()		{ return soundVolume.get(); }
//	default boolean playSounds()	{ return playSounds.get(); }
	
	// Do no use alone, Go through musicVolume
	ParamBoolean playMusic = new PlayMusic();
	class PlayMusic extends ParamBoolean {
		PlayMusic() {
			super(GAME_UI, "MUSIC", true);
			isDuplicate(true);
			isCfgFile(true);
		}
		@Override public Boolean getOption()		{ return playMusic(); }
		@Override public void setOption(Boolean b)	{
			if(playMusic() != b)
				//playMusic(!s); // ! because following line will toggle!
				SoundManager.current().toggleMusic();
		}
	}

	ParamBoolInt musicVolume = new MusicVolume();
	class MusicVolume extends ParamBoolInt {
		MusicVolume() {
			super(GAME_UI, playMusic, "MUSIC_ON", "MUSIC_OFF", 10, 0, 10);
			isDuplicate(true);
			isCfgFile(true);
		}
		@Override public String getCfgLabel()		{ return "MUSIC_VOLUME"; }
		@Override public Integer getOption()		{ return musicVolume(); }
		@Override public void setOption(Integer i)	{
			musicVolume(Math.abs(i));
			SoundManager.current().resetMusicVolumes();
		}
	}
//	default int musicVolume()		{ return musicVolume.get(); }
//	default boolean playMusic()		{ return playMusic.get(); }

	ParamInteger selectedScreen = new SelectedScreen();
	class SelectedScreen extends ParamInteger {
		SelectedScreen() {
			super(GAME_UI, "SELECTED_SCREEN", -1);
			setLimits(-1, Rotp.maxScreenIndex());
			loop(true);
			isDuplicate(true);
			isCfgFile(true);
		}
		@Override public Integer getOption()		{ return selectedScreen(); }
		@Override public void setOption(Integer i)	{ selectedScreen(i); }
	}
//	default int selectedScreen()		{ return selectedScreen.get(); }

	ParamInteger backupTurns = new BackupTurns();
	class BackupTurns extends ParamInteger {
		BackupTurns() {
			super(GAME_UI, "BACKUP", 5);
			setLimits(0, MAX_BACKUP_TURNS);
			setIncrements(1, 5, 10);
			isDuplicate(true);
			isCfgFile(true);
			specialZero(GAME_UI + "BACKUP_OFF");
		}
		@Override public String getCfgLabel()		{ return "BACKUP_TURNS"; }
		@Override public Integer getOption()		{ return backupTurns(); }
		@Override public void setOption(Integer i)	{ backupTurns(i); }
	}

	//	default int backupTurns()		{ return backupTurns.get(); }
	ParamInteger backupKeep	= new ParamInteger(GAME_UI, "BACKUP_KEEP", -1)
			.setLimits(-1, 100)
			.setIncrements(1, 5, 10)
			.isDuplicate(false)
			.isCfgFile(true)
			.specialNegative(GAME_UI + "BACKUP_KEEP_ALL")
			.loop(true);
	default int backupKeep()		{ return backupKeep.get(); }
	default boolean deleteBackup()	{ return !backupKeep.isSpecialNegative(); }

	ParamString saveDirectory = new SaveDirectory();
	class SaveDirectory extends ParamString {
		SaveDirectory() {
			super(GAME_UI, "SAVEDIR", "");
			isDuplicate(true);
			isCfgFile(true);
		}
		@Override protected String descriptionId()	{
			String es = get().isEmpty()? "1" : "2";
			String label = super.descriptionId() + es;
			return label;			
		}
		@Override public String getGuiDescription()	{ return langLabel(descriptionId(), get()); }
		@Override public String guideValue()		{
			String es = get().isEmpty()? "_DEFAULT" : "_CUSTOM";
			String label = getLangLabel() + es;
			return langLabel(label);
		}
		@Override public boolean toggle(MouseEvent e, BaseModPanel frame)	{
			if (getDir(e) == 0) {
				set("");
				return false;
			}
	        final JFileChooser fc = new JFileChooser();
	        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
	        File saveDir = new File(saveDirectoryPath());
	        fc.setCurrentDirectory(saveDir);
	        int returnVal = fc.showOpenDialog(frame);
	        if (returnVal == JFileChooser.APPROVE_OPTION) {
	            String path = fc.getSelectedFile().getAbsolutePath();
	            set(path);
	        }
			return false;
	    }
		@Override public String	getCfgLabel()		{ return "SAVE_DIR"; }
		@Override public String	getOption()			{ return saveDir(); }
		@Override public void	setOption(String s)	{ saveDir(s); }
	}
	//	default int saveDir()		{ return saveDirectory.get(); }

	ParamBoolean disableAdvisor = new DisableAdvisor();
	class DisableAdvisor extends ParamBoolean {
		DisableAdvisor() {
			super(MOD_UI, "DISABLE_ADVISOR", false);
			isDuplicate(true);
			isCfgFile(true);
		}
		@Override public Boolean getOption()		{ return disableAdvisor(); }
		@Override public void setOption(Boolean b)	{ disableAdvisor(b); }
	}

	ParamList disableAutoHelp = new ParamList( MOD_UI, "DISABLE_AUTO_HELP", "Yes")
			.isCfgFile(true)
			.showFullGuide(true)
			.put("Yes",		MOD_UI + "DISABLE_AUTO_HELP_YES")
			.put("No",		MOD_UI + "DISABLE_AUTO_HELP_NO")
			.put("Never",	MOD_UI + "DISABLE_AUTO_HELP_NEVER");
	default boolean isAutoHelpDisabled()	{ return disableAutoHelp.get().equalsIgnoreCase("Yes"); }
	default void autoHelpHasBeenShown()		{
		if (disableAutoHelp.get().equalsIgnoreCase("No"))
			disableAutoHelp.set("Yes");
	}

	ParamBoolean showGuide = new ParamBoolean(MOD_UI, "SHOW_GUIDE", true).isCfgFile(true);
	default boolean showGuide()			{ return showGuide.get(); }
	
	// ==================== Mod Options ====================
	//
	ParamOptions menuStartup	= new ParamOptions(MOD_UI, "MENU_STARTUP", ParamOptions.LAST).isCfgFile(true);
	ParamBoolean useFusionFont	= new UseFusionFont();
	class UseFusionFont extends ParamBoolean {
		UseFusionFont() {
			super(MOD_UI, "USE_FUSION_FONT", false);
			isCfgFile(true);
		}
		@Override public void setOption(Boolean val) { FontManager.INSTANCE.resetGalaxyFont(); }
	}

	ParamList compactOptionOnly = new ParamList( MOD_UI, "COMPACT_OPTION_ONLY", "Yes")
			.isCfgFile(true)
			.showFullGuide(true)
			.put("Yes",	MOD_UI + "COMPACT_OPTION_ONLY_YES")
			.put("No",	MOD_UI + "COMPACT_OPTION_ONLY_NO");
	default ParamList compactOptionOnly()	{ return compactOptionOnly; }
	
	ParamList gameOverTitles	= new ParamList( MOD_UI, "GAME_OVER_TITLE", "Original")
			.isCfgFile(true)
			.isDuplicate(false)
			.showFullGuide(true)
			.put("Original",	 MOD_UI + "GAME_OVER_TITLE_ORIGINAL")
			.put("Extended",	 MOD_UI + "GAME_OVER_TITLE_EXTENDED")
			.put("RealmsBeyond", MOD_UI + "GAME_OVER_TITLE_RB");
	default String gameOverTitlesKeyExt() {
		switch (gameOverTitles.get().toUpperCase()) {
			case "EXTENDED" :		return "_E";
			case "REALMSBEYOND" :	return "_RB";
			default:				return "";
		}
	}
	default boolean originalGOTitles()	{ return gameOverTitles.get().equalsIgnoreCase("Original"); }
	default boolean extendedGOTitles()	{ return gameOverTitles.get().equalsIgnoreCase("Extended"); }
	default boolean rbGOTitles()		{ return gameOverTitles.get().equalsIgnoreCase("RealmsBeyond"); }

	ParamBoolean showAlternateAnimation	= new ParamBoolean(MOD_UI, "SHOW_ALT_ANIMATION", true).isCfgFile(true);
	ParamBoolean originalSpeciesOnly	= new ParamBoolean(MOD_UI, "ORIGINAL_SPECIES_ONLY", true).isCfgFile(true);
	default boolean originalSpeciesOnly()	{ return originalSpeciesOnly.get(); }

	ParamBoolean  noFogOnIcons	= new ParamBoolean( BASE_UI, "NO_FOG_ON_ICONS", false).isCfgFile(true);
	default boolean noFogOnIcons()	{ return noFogOnIcons.get(); }

	ParamFloat   showFlagFactor	= new ShowFlagFactor();
	class ShowFlagFactor extends ParamFloat {
		ShowFlagFactor() {
			super(MOD_UI, "SHOW_FLAG_FACTOR", 1.0f);
			setLimits(0.3f, 3f);
			setIncrements(0.01f, 0.05f, 0.2f);
			cfgFormat("%");
			guiFormat("%");
			isCfgFile(true);
		}
		@Override public void setOption(Float val) { maxFlagScale((int) (80 * val)); }
	}

	ParamFloat   showPathFactor	= new ShowPathFactor();
	class ShowPathFactor extends ParamFloat {
		ShowPathFactor() {
			super(MOD_UI, "SHOW_PATH_FACTOR", 1.0f);
			setLimits(0.3f, 3f);
			setIncrements(0.01f, 0.05f, 0.2f);
			cfgFormat("%");
			guiFormat("%");
			isCfgFile(true);
		}
		@Override public void setOption(Float val) { maxRallyScale((int) (100 * val)); }
	}

	ParamInteger showNameMinFont	= new ShowNameMinFont();
	class ShowNameMinFont extends ParamInteger {
		ShowNameMinFont() {
			super(MOD_UI, "SHOW_NAME_MIN_FONT", 8);
			setLimits(2, 24);
			setIncrements(1, 2, 5);
			isCfgFile(true);
		}
		@Override public void setOption(Integer val) {
			setMinFont(val);
			setMinFont2(Math.round(val/showInfoFontRatio.get()));
		}
	}

	ParamFloat showInfoFontRatio	= new ShowInfoFontRatio();
	class ShowInfoFontRatio extends ParamFloat {
		ShowInfoFontRatio() {
			super(MOD_UI, "SHOW_INFO_FONT_RATIO", 0.7f);
			setLimits(0.2f, 3f);
			setIncrements(0.01f, 0.05f, 0.2f);
			cfgFormat("%");
			guiFormat("%");
			isCfgFile(true);
		}
		@Override public void setOption(Float val) { setMinFont2(Math.round(getMinFont()/val)); }
	}

	ParamFloat mapFontFactor	= new MapFontFactor();
	class MapFontFactor extends ParamFloat {
		MapFontFactor() {
			super(MOD_UI, "MAP_FONT_FACTOR", 1.0f);
			setLimits(0.3f, 3f);
			setIncrements(0.01f, 0.05f, 0.2f);
			cfgFormat("%");
			guiFormat("%");
			isCfgFile(true);
		}
		@Override public void setOption(Float val) { setFontPct(Math.round(val * 100)); }
	}

	ParamFloat showFleetFactor	= new ShowFleetFactor();
	class ShowFleetFactor extends ParamFloat {
		ShowFleetFactor() {
			super(MOD_UI, "SHOW_FLEET_FACTOR", 1.0f);
			setLimits(0.3f, 3f);
			setIncrements(0.01f, 0.05f, 0.2f);
			cfgFormat("%");
			guiFormat("%");
			isCfgFile(true);
		}
		@Override public void setOption(Float val) {
			maxStargateScale		((int) (40 * val));
			maxFleetUnarmedScale	((int) (40 * val));
			maxFleetTransportScale	((int) (60 * val));
			maxFleetSmallScale		((int) (60 * val));
			maxFleetLargeScale		((int) (80 * val));
			maxFleetHugeScale		((int) (100 * val));	
		}
	}

	ParamBoolean showPendingOrders	= new ParamBoolean(MOD_UI, "SHOW_PENDING_ORDERS", true)
			.isCfgFile(true);
	default boolean showPendingOrders()		{ return showPendingOrders.get(); }

	ParamBoolean raceStatusLog		= new ParamBoolean(MOD_UI, "RACE_STATUS_LOG", false).isCfgFile(true);
	default boolean selectedRaceStatusLog()	{ return raceStatusLog.get(); }
	default void	toggleRaceStatusLog()	{ raceStatusLog.toggle(); }

	ParamInteger galaxyPreviewColorStarsSize = new ParamInteger(MOD_UI, "GALAXY_PREVIEW_COLOR_SIZE", 5)
			.setLimits(0, 20)
			.setIncrements(1, 2, 5)
			.isCfgFile(true);
	default ParamInteger galaxyPreviewColorStarsSize()	{ return galaxyPreviewColorStarsSize; }

	ParamInteger minListSizePopUp	= new ParamInteger(MOD_UI, "MIN_LIST_SIZE_POP_UP" , 8)
			.setLimits(0, 10)
			.loop(true)
			.isCfgFile(true)
			.specialZero(MOD_UI + "MIN_LIST_SIZE_POP_UP_NEVER");
	default ParamInteger minListSizePopUp()	{ return minListSizePopUp; }

	ParamList colorSet = new ColorSet() {
	}		.isCfgFile(true)
			.showFullGuide(true)
			.put("Brown",	MOD_UI + "COLOR_SET_BROWN")
			.put("Grey",		MOD_UI + "COLOR_SET_GREY");
	class ColorSet extends ParamList {
		ColorSet() {
			super(MOD_UI, "COLOR_SET", "Brown");
			isCfgFile(true);
			showFullGuide(true);
			put("Brown",	MOD_UI + "COLOR_SET_BROWN");
			put("Grey",		MOD_UI + "COLOR_SET_GREY");
		}
		@Override public void setFromCfgValue(String val) {
			super.setFromCfgValue(val);
			GameUI.colorSet(selectedColorSet());
		}
		@Override public void setOption(String val) {
			super.setOption(val);
			GameUI.colorSet(selectedColorSet());
			if (RotPUI.instance() == null)
				return;
			MainOptionsUI mo = RotPUI.mainOptionsUI();
			if (mo.activeList() == null)
				return;
			mo.clearImages();
			mo.refreshGui();
		}
	}
	static int selectedColorSet()	{
		switch (colorSet.get().toUpperCase()) {
			case "BROWN":	return 0;
			case "GREY":	return 1;
			default:		return 0;
		}
	}

	ParamList raceStatusView		= new ParamList( MOD_UI, "RACE_STATUS_VIEW", "PctTotal")
			.isCfgFile(true)
			.showFullGuide(true)
			.put("PctTotal",	MOD_UI + "RACE_STATUS_VIEW_PCT_TOTAL")
			.put("PctPlayer",	MOD_UI + "RACE_STATUS_VIEW_PCT_PLAYER")
			.put("Value",		MOD_UI + "RACE_STATUS_VIEW_VALUE");
	default void	toggleRaceStatusView()	{ raceStatusView.next(); }
	default String	raceStatusViewText()	{ return raceStatusView.guideValue(); }
	default boolean raceStatusViewTotal()	{ return raceStatusView.get().equals("PctTotal"); }
	default boolean raceStatusViewPlayer()	{ return raceStatusView.get().equals("PctPlayer"); }
	default boolean raceStatusViewValue()	{ return raceStatusView.get().equals("Value"); }

	ParamInteger realNebulaSize	= new RealNebulaeSize();
	class RealNebulaeSize extends ParamInteger {
		RealNebulaeSize() {
			super(MOD_UI, "REAL_NEBULAE_SIZE", 0);
			setLimits(0, 5);
			setIncrements(1, 1, 1);
			isCfgFile(false);
			specialZero(MOD_UI + "REAL_NEBULAE_NO");
			loop(true);
		}
		@Override public Integer set(Integer i)	{
			rotp.model.galaxy.Nebula.requestedQuality(i);
			super.set(i);
			UserPreferences.save();
			return get();
		}
	}
	default int selectedRealNebulaSize()	{ return realNebulaSize.get(); }
	default boolean selectedRealNebula()	{ return realNebulaSize.get() != 0; }
	default ParamInteger getRealNebulaSize()	{ return realNebulaSize; }

	ParamBoolean realNebulaShape	= new ParamBoolean(MOD_UI, "REAL_NEBULAE_SHAPE", true).isCfgFile(false);
	default boolean realNebulaShape()	{ return realNebulaShape.get(); }
	default ParamBoolean getRealNebulaShape()	{ return realNebulaShape; }

	ParamInteger realNebulaeOpacity	= new ParamInteger(MOD_UI, "REAL_NEBULAE_OPACITY", 60)
			.setLimits(10, 100)
			.setIncrements(1, 5, 20)
			.isCfgFile(true);
	default float realNebulaeOpacity()	{ return realNebulaeOpacity.get()/100f; }

	ParamBoolean showAllAI	= new ShowAllAI();
	class ShowAllAI extends ParamBoolean {
		ShowAllAI() {
			super(MOD_UI, "SHOW_ALL_AI", true);
			isCfgFile(true);
			isDuplicate(false);
		}
		@Override public Boolean set(Boolean newValue) {
			super.set(newValue);
			rotp.ui.game.SetupGalaxyUI.specificAI().reInit(null);
			rotp.ui.game.SetupGalaxyUI.opponentAI().reInit(null);
			rotp.model.game.IAdvOptions.autoplay.reInit(null);
			return get();
		}
	}

	default boolean selectedShowAllAI()			{ return showAllAI.get(); }
	String NAME_UI		= "NAME_";
	// Customized nominal names
	ParamSpeciesName altairi	= new ParamSpeciesName(NAME_UI, "RACE_ALKARI",	 "Alkari Sovereignty, Alkari, Alkaris");
	ParamSpeciesName ursinathi	= new ParamSpeciesName(NAME_UI, "RACE_BULRATHI", "Bulrathi Empire, Bulrathi, Bulrathis");
	ParamSpeciesName nazlok		= new ParamSpeciesName(NAME_UI, "RACE_DARLOK",	 "Darloks, Darlok, Darloks");
	ParamSpeciesName human		= new ParamSpeciesName(NAME_UI, "RACE_HUMAN",	 "Human Triumvirate, Human, Humans");
	ParamSpeciesName kholdan	= new ParamSpeciesName(NAME_UI, "RACE_KLACKON",	 "Klackon Hive, Klackon, Klackons");
	ParamSpeciesName meklonar	= new ParamSpeciesName(NAME_UI, "RACE_MEKLAR",	 "Meklar Dominion, Meklar, Meklars");
	ParamSpeciesName fiershan	= new ParamSpeciesName(NAME_UI, "RACE_MRRSHAN",	 "Mrrshan Clan, Mrrshan, Mrrshan");
	ParamSpeciesName mentaran	= new ParamSpeciesName(NAME_UI, "RACE_PSILON",	 "Psilon Republic, Psilon, Psilons");
	ParamSpeciesName ssslaura	= new ParamSpeciesName(NAME_UI, "RACE_SAKKRA",	 "Sakkra Conclave, Sakkra, Sakkras");
	ParamSpeciesName cryslonoid	= new ParamSpeciesName(NAME_UI, "RACE_SILICOID", "Silicoid Imperium, Silicoid, Silicoids");

	HashMap<String, ParamSpeciesName> speciesNameMap = speciesNameMap();
	static HashMap<String, ParamSpeciesName> speciesNameMap() {
		HashMap<String, ParamSpeciesName> map= new HashMap<>();
		map.put(human.getCfgLabel(),		human);
		map.put(altairi.getCfgLabel(),		altairi);
		map.put(cryslonoid.getCfgLabel(),	cryslonoid);
		map.put(fiershan.getCfgLabel(),		fiershan);
		map.put(kholdan.getCfgLabel(),		kholdan);
		map.put(meklonar.getCfgLabel(),		meklonar);
		map.put(mentaran.getCfgLabel(),		mentaran);
		map.put(nazlok.getCfgLabel(),		nazlok);
		map.put(ssslaura.getCfgLabel(),		ssslaura);
		map.put(ursinathi.getCfgLabel(),	ursinathi);
		return map;
	}

	ParamBoolean moo1SpeciesName = new Moo1SpeciesName();
	class Moo1SpeciesName extends ParamBoolean {
		Moo1SpeciesName() {
			super(MOD_UI, "MOO1_SPECIES_NAME", false);
			isCfgFile(false);
			isDuplicate(false);
		}
		@Override public boolean toggle(MouseEvent e, MouseWheelEvent w, BaseModPanel frame) {
			human.set("Human Triumvirate, Human, Humans");
			altairi.set("Alkari Sovereignty, Alkari, Alkaris");
			cryslonoid.set("Silicoid Imperium, Silicoid, Silicoids");
			fiershan.set("Mrrshan Clan, Mrrshan, Mrrshan");
			kholdan.set("Klackon Hive, Klackon, Klackons");
			meklonar.set("Meklar Dominion, Meklar, Meklars");
			mentaran.set("Psilon Republic, Psilon, Psilons");
			nazlok.set("Darloks, Darlok, Darloks");
			ssslaura.set("Sakkra Conclave, Sakkra, Sakkras");
			ursinathi.set("Bulrathi Empire, Bulrathi, Bulrathis");
			next();
			LanguageManager.current().reloadLanguage();
			return true;
		}
		@Override public String	guideValue() { return ""; }
	}

	ParamBoolean clearSpeciesName	= new ClearSpeciesName();
	class ClearSpeciesName extends ParamBoolean {
		ClearSpeciesName() {
			super(MOD_UI, "CLEAR_SPECIES_NAME", false);
			isCfgFile(false);
			isDuplicate(false);
		}
		@Override public boolean toggle(MouseEvent e, MouseWheelEvent w, BaseModPanel frame) {
			for (ParamSpeciesName param : speciesNameMap.values()) {
				param.set("");
				param.updated(true);
			}
			next();
			LanguageManager.current().reloadLanguage();
			return true;
		}
		@Override public String	guideValue() { return ""; }
	}

	ParamBoolean activateSpeciesName	= new ActivateSpeciesName();
	class ActivateSpeciesName extends ParamBoolean {
		ActivateSpeciesName() {
			super(MOD_UI, "ACTIVATE_SPECIES_NAME", false);
			isCfgFile(false);
			isDuplicate(false);
		}
		@Override public boolean toggle(MouseEvent e, MouseWheelEvent w, BaseModPanel frame) {
			next();
			LanguageManager.current().reloadLanguage();
			return false;
		}
		@Override public String	guideValue() { return ""; }
	}

	ParamList defaultSettings	= new DefaultSettings();
	class DefaultSettings extends ParamList {
		DefaultSettings() {
			super(MOD_UI, "DEFAULT_SETTINGS",
					Arrays.asList(
							FUSION_DEFAULT,
							MOO1_DEFAULT,
							ROTP_DEFAULT
							),
					FUSION_DEFAULT);
			setDefaultValue(MOO1_DEFAULT, MOO1_DEFAULT);
			setDefaultValue(ROTP_DEFAULT, ROTP_DEFAULT);
			isDuplicate(true);
			isCfgFile(true);
			showFullGuide(true);
		}
		@Override public String getCfgValue()		{ return DEF_VAL.getSettingName(); }
		@Override public String getOption()			{ return DEF_VAL.defVal(); }
		@Override public void setOption(String s)	{ DEF_VAL.selectedDefault(s); }
	}
	// ==================== GUI List Declarations ====================
	//
	String NAME_GUI_ID	= "NAME_OPTIONS";
	static ParamSubUI specieNameOptionsUI() {
		return new ParamSubUI( MOD_UI, "NAME_OPTIONS_UI", specieNameOptionsMap(),
				"NAME_OPTIONS_TITLE", NAME_GUI_ID).isCfgFile(true);
	}
	static SafeListPanel specieNameOptionsMap()	{
		SafeListPanel map = new SafeListPanel();
		map.add(new SafeListParam(Arrays.asList(
				altairi, ursinathi,
				nazlok, human,
				kholdan, meklonar,
				fiershan, mentaran,
				ssslaura, cryslonoid,

				headerSpacer,
				moo1SpeciesName,
				clearSpeciesName,

				headerSpacer,
				activateSpeciesName
				)));
		return map;
	}
	static SafeListParam vanillaSettingsUI() {
		SafeListParam options  = new SafeListParam(
				Arrays.asList(
						displayMode, graphicsMode,
						texturesMode, sensitivityMode,
						selectedScreen,

						null,
						soundVolume, musicVolume,
						//debugShowMemory,
						colorSet, gameOverTitles,
						defaultSettings,
						
						null,
						// IConvenienceOptions.autoColonize_, IConvenienceOptions.autoBombard_,
						backupTurns, backupKeep, saveDirectory,
						originalSpeciesOnly, showAllAI,

						null,
						disableAutoHelp, disableAdvisor,
						commonOptionsUI(),
						ICombatOptions.combatOptionsUI(),
						IDebugOptions.debugOptionsUI()
						));
		return options;
	}
	static SafeListPanel commonOptionsMap()	{
		SafeListPanel map = new SafeListPanel();
		map.add(new SafeListParam(Arrays.asList(
				new ParamTitle("COMPUTER_OPTIONS"),
				graphicsMode, texturesMode, sensitivityMode,
				soundVolume, musicVolume,

				headerSpacer,
				new ParamTitle("MENU_APPEARANCE"),
				colorSet, galaxyPreviewColorStarsSize,
				minListSizePopUp, menuStartup,
				noFogOnIcons, showAlternateAnimation,
				useFusionFont, compactOptionOnly
				)));
		map.add(new SafeListParam(Arrays.asList(
				new ParamTitle("ZOOM_FONT"),
				mapFontFactor, showNameMinFont, showInfoFontRatio,
				
				headerSpacer,
				new ParamTitle("ZOOM_FLEET"),
				showFleetFactor, showFlagFactor, showPathFactor,
				
				headerSpacer,
				new ParamTitle("ZOOM_REPLAY"),
				finalReplayZoomOut, empireReplayZoomOut, replayTurnPace
				)));
		map.add(new SafeListParam(Arrays.asList(
				new ParamTitle("BACKUP_OPTIONS"),
				backupTurns, backupKeep, saveDirectory,

				headerSpacer,
				new ParamTitle("GAME_UI_PREFERENCES"),
				showPendingOrders,
				raceStatusLog, disableAdvisor, disableAutoHelp,
				originalSpeciesOnly,

				// headerSpacer,
				// new ParamTitle("GAME_VARIOUS"),
				headerSpacer,
				new ParamTitle("SUB_PANEL_OPTIONS"),
				IDebugOptions.debugOptionsUI(),
				ICombatOptions.combatOptionsUI(),
				specieNameOptionsUI()
				)));
		return map;
	};
	String COMMON_GUI_ID	= "COMMON_OPTIONS";
	static ParamSubUI commonOptionsUI() {
		return new ParamSubUI( MOD_UI, COMMON_GUI_ID, commonOptionsMap())
				.isCfgFile(false);
	}
}
