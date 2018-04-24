package tdt4140.gr1878.app.ui;

import fxmapcontrol.Map;
import fxmapcontrol.AzimuthalEquidistantProjection;
import fxmapcontrol.BingMapsTileLayer;
import fxmapcontrol.EquirectangularProjection;
import fxmapcontrol.GnomonicProjection;
import fxmapcontrol.ImageFileCache;
import fxmapcontrol.Location;
import fxmapcontrol.MapBase;
import fxmapcontrol.MapGraticule;
import fxmapcontrol.MapItemsControl;
import fxmapcontrol.MapNode;
import fxmapcontrol.MapPolygon;
import fxmapcontrol.MapProjection;
import fxmapcontrol.StereographicProjection;
import fxmapcontrol.MapTileLayer;
import fxmapcontrol.TileImageLoader;
import fxmapcontrol.WebMercatorProjection;
import fxmapcontrol.WmsImageLayer;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;
import tdt4140.gr1878.app.ui.TrackingController;

import ch.qos.logback.core.net.SyslogOutputStream;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import tdt4140.gr1878.app.ui.MapMarker;
import tdt4140.gr1878.app.ui.db.Database;

public class FXMLController extends MainController implements Initializable  {

    MapItemsControl<MapNode> markersParent;
    @FXML
	MapBase mapView;
	@FXML
    private MapBase map;
    @FXML
    private WmsImageLayer wmsLayer;
    @FXML
    private MapGraticule mapGraticule;
    @FXML
    private Slider zoomSlider;
    @FXML
    private ComboBox mapLayerComboBox;
    @FXML
    private ComboBox projectionComboBox;
    @FXML
    private MapNode StartNode;
    @FXML
    private MapNode EndNode;
    @FXML
    private MapNode Node2;
    @FXML
    private MapNode Node1;
    
   	MapPathLine prevStroke;
    
	protected Stage myStage;
	protected Scene logonScene;
	protected Database db;
	protected Main main;
	private List<Double> coordList =  new ArrayList<Double>();

	

	public FXMLController(Stage primaryStage, Database db, Main main) {
		super(primaryStage, db, main);
	}

    //Har lagt inn tre eksempler på joggeturer i tre ulike byer.
    public void getRanBy() {
    	coordList = TrackingController.getCoordinates(TrackingController.getRouteNumber(), TrackingController.getTRouteNumber());
    	StartNode.setLocation(new Location(coordList.get(0), coordList.get(1)));
    	Node1.setLocation(new Location(coordList.get(2), coordList.get(3)));
    	Node2.setLocation(new Location(coordList.get(4), coordList.get(5)));
	  	EndNode.setLocation(new Location(coordList.get(6), coordList.get(7)));
    }
    
    public void randomNode() {
    	getRanBy();
       	MapPathLine stroke = new MapPathLine(StartNode,Node1);
       	MapPathLine stroke2 = new MapPathLine(Node1,Node2);
       	MapPathLine stroke3 = new MapPathLine(Node2,EndNode);

       	if(prevStroke==null) {
       		map.getChildren().add(stroke);	
       		map.getChildren().add(stroke2);
       		map.getChildren().add(stroke3);	
       		prevStroke = stroke;
       	}else {
       		map.getChildren().remove(prevStroke);
       		prevStroke=null;
       		map.getChildren().add(stroke);
       		map.getChildren().add(stroke2);	
       		map.getChildren().add(stroke3);	 


       	}
    }
	
	public void initialize() { 
		myStage.sceneProperty().addListener((observable, oldval, newval) -> {
			if (newval != null && this.logonScene == null)
				this.logonScene = newval;
		});
		
		mapView.getChildren().add(MapTileLayer.getOpenStreetMapLayer());
		zoomSlider.valueProperty().addListener((prop, oldValue, newValue) -> {
			mapView.setZoomLevel(400);			
		});
		markersParent = new MapItemsControl<MapNode>();
		mapView.getChildren().add(markersParent);
		randomNode();

		
	}
	
	
	//Kjører randomNode som tegner stien, når man dobbelklikker på kartet. Dette skal endres til å skje når man trykker end workout. 
    @FXML
    private void handlePressed(MouseEvent event) {
        if (event.getTarget() == map && event.getClickCount() == 2) {
        	randomNode();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    	

        TileImageLoader.setCache(new ImageFileCache());

        map.targetZoomLevelProperty().bindBidirectional(zoomSlider.valueProperty());


        map.getChildren().remove(wmsLayer);

        HashMap<String, Node> mapLayers = new HashMap<>();
        mapLayers.put("OpenStreetMap", MapTileLayer.getOpenStreetMapLayer());
        //Ta forandre her idk om riktig, noen bugs
        mapLayers.put("Seamarks", new MapTileLayer("Seamarks", "http://tiles.openseamap.org/seamark/{z}/{x}/{y}.png", 9, 18));
        mapLayers.put("OpenStreetMap WMS", new WmsImageLayer("http://ows.terrestris.de/osm/service", "OSM-WMS"));

        mapLayerComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                Node mapLayer = mapLayers.get((String) newValue);
                
                if (mapLayers.containsValue(map.getChildren().get(0))) {
                    map.getChildren().set(0, mapLayer);
                } else {
                    map.getChildren().add(0, mapLayer);
                }

                //PrÃ¸v google ikke bing, todo
                if (mapLayer instanceof BingMapsTileLayer
                        && ((BingMapsTileLayer) mapLayer).getMapMode() != BingMapsTileLayer.MapMode.Road) {
                    map.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
                    mapGraticule.setStroke(Color.WHITE);
                    mapGraticule.setTextFill(Color.WHITE);
                } else {
                    map.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
                    mapGraticule.setStroke(Color.BLACK);
                    mapGraticule.setTextFill(Color.BLACK);
                }
            }
        });
        mapLayerComboBox.getSelectionModel().select(0);


        
        //Trengs ikke tbh
        MapProjection[] projections = new MapProjection[]{
            new WebMercatorProjection(),
            new EquirectangularProjection(),
            new GnomonicProjection(),
            new StereographicProjection()
        };

       // projectionComboBox.getSelectionModel().select(0);
      //  projectionComboBox.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
           // if (newValue != null) {
                //int index = newValue.intValue();
                map.setProjection(projections[0]);
          //  }
     //   });

                //Map layers, "kule"
        for (double lon = -180d; lon < 180d; lon += 15) {
            MapPolygon polygon = new MapPolygon();
            polygon.getLocations().add(new Location(0d, lon - 5d));
            polygon.getLocations().add(new Location(-5d, lon));
            polygon.getLocations().add(new Location(0d, lon + 5d));
            polygon.getLocations().add(new Location(5d, lon));
            polygon.setLocation(new Location(0d, lon));
            polygon.setStroke(Color.RED);

            map.getChildren().add(polygon);
        }

		randomNode();

        for (double lon = -180d; lon < 180d; lon += 15) {
            String text = lon < 0d ? String.format(" W %.0f ", -lon) : String.format(" E %.0f ", lon);

            Label label = new Label(text);
            label.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
            label.setTranslateY(-20);

            MapNode pushpin = new MapNode();
            pushpin.setLocation(new Location(0, lon));
            pushpin.getChildren().add(label);

            map.getChildren().add(pushpin);
        }
       
    }


}