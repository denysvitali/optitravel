package ch.supsi.dti.i2b.shrug.optitravel.ui;

import ch.supsi.dti.i2b.shrug.optitravel.models.*;
import ch.supsi.dti.i2b.shrug.optitravel.models.plan.PlanSegment;
import de.jensd.fx.glyphs.materialicons.MaterialIcon;
import de.jensd.fx.glyphs.materialicons.MaterialIconView;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;

import java.io.IOException;

public class PlanSegmentCellController {

    @FXML
    private Label from;
    @FXML
    private Label from_time;
    @FXML
	private Label to_time;
    @FXML
    private Label to;
    @FXML
    private Label distance;
    @FXML
    private Label details;
    @FXML
    private MaterialIconView icon;

    private Node view;

    public PlanSegmentCellController() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/layouts/listCellItem.fxml"));
        loader.setController(this);
        try {
            view = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

	private void setFromText(Stop s, Location l){
		if(s!=null) {
			from.setText(s.getName());
		} else {
			from.setText(l.toString());
		}
	}

    private void setToText(Stop s, Location l){
		if(s!=null) {
			to.setText(s.getName());
		} else {
			to.setText(l.toString());
		}
	}

    public void setTrip(PlanSegment planSegment) {
        distance.setVisible(false);
        details.setVisible(false);

        Location start_location = planSegment.getStart().getLocation();
        Stop start_stop = null;

		Location end_location = planSegment.getEnd().getLocation();
		Stop end_stop = null;


		if(start_location instanceof Stop){
            start_stop = (Stop) start_location;
        }

		if(end_location instanceof Stop){
			end_stop = (Stop) end_location;
		}

		String from_time_string = planSegment.getStart().getTime().prettyFormat();
		String to_time_string = planSegment.getEnd().getTime().prettyFormat();

        if(planSegment.getTrip() instanceof WaitingTrip) {
        	WaitingTrip t = (WaitingTrip) planSegment.getTrip();
            from.setText(String.format(
            		"Wait %d minutes for your connection",
					(int) t.getWaitTime())
			);
			setToText(end_stop, end_location);
			from_time.setText(from_time_string);
			to_time.setText(to_time_string);
            icon.setIcon(MaterialIcon.TIMER);
        } else if (planSegment.getTrip() instanceof WalkingTrip || planSegment.getTrip() instanceof ConnectionTrip) {
            from.setText("Walk to the next stop");
			setToText(end_stop, end_stop);
            from_time.setText(from_time_string);
			to_time.setText(to_time_string);
            icon.setIcon(MaterialIcon.DIRECTIONS_WALK);
        } else {
            icon.setIcon(getIcon(planSegment.getTrip().getRoute().getType()));
            from.setText(planSegment.getStart().toString());
            setFromText(start_stop, start_location);
			setToText(end_stop, end_location);
            from_time.setText(from_time_string);
            to_time.setText(to_time_string);

            String hs = planSegment.getTrip().getHeadSign();
            if(hs == null || hs.equals("")){
				String route_shortname = planSegment.getTrip()
						.getRoute().getName();
				if(route_shortname != null && !route_shortname.equals("")){
					details.setText(route_shortname);
					details.setVisible(true);
				}
			} else {
				details.setText(hs);
				details.setVisible(true);
			}

        }
    }

    private MaterialIcon getIcon(RouteType rt){
    	if(rt == null){
    		return MaterialIcon.DIRECTIONS_TRANSIT;
		}
    	rt = rt.getRouteCategory();
    	switch(rt){
			case BUS:
			case BUS_SERVICE:
			case COACH_SERVICE:
				return MaterialIcon.DIRECTIONS_BUS;
			case UNDERGROUND_SERVICE:
			case METRO_SERVICE:
			case SUBWAY:
				return MaterialIcon.DIRECTIONS_SUBWAY;
			case RAIL:
			case RAILWAY_SERVICE:
				return MaterialIcon.DIRECTIONS_RAILWAY;
		}

		return MaterialIcon.DIRECTIONS_TRANSIT;
	}

    public Node getView() {
        return view;
    }
}
