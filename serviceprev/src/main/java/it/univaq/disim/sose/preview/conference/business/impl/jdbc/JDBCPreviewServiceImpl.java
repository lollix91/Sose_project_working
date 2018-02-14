package it.univaq.disim.sose.preview.conference.business.impl.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.univaq.disim.sose.preview.conference.business.BusinessException;
import it.univaq.disim.sose.preview.conference.business.PreviewService;
import it.univaq.disim.sose.preview.conference.business.model.Conference;

@Service
public class JDBCPreviewServiceImpl implements PreviewService {
	
	private static Logger LOGGER = LoggerFactory.getLogger(JDBCPreviewServiceImpl.class);
	
	@Autowired
	private DataSource dataSource;

	@Override
	public List<Conference> getConferences(List<String> conferences) throws BusinessException {
		// TODO Auto-generated method stub
		
		String join = "'" + StringUtils.join(conferences,"','") + "'";
		
		String sql = "SELECT conference_id, name, conference_abstract, image, pdf, city, date, latitude, longitude FROM conferences WHERE conference_id in ("+join+")";
	    LOGGER.info(sql);
	    
		List<Conference> result = new ArrayList<>();
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		
		try {
		con = dataSource.getConnection();
		st = con.createStatement();
		
		rs = st.executeQuery(sql);
		
		while (rs.next()) {
			Conference preview = new Conference();
			preview.setName(rs.getString("name"));
			preview.setConferenceAbstract(rs.getString("conference_abstract"));
			preview.setImage(rs.getString("image"));
			preview.setPdf(rs.getString("pdf"));
			preview.setCity(rs.getString("city"));
			preview.setDate(rs.getDate("date"));
			preview.setLatitude(rs.getDouble("latitude"));
			preview.setLongitude(rs.getDouble("longitude"));
			result.add(preview);
		}
	
		} catch (SQLException e) {
		e.printStackTrace();
		throw new BusinessException(e);
		} finally {
		if (st != null) {
			try {
				st.close();
			} catch (SQLException e) {
			}
		}
		if (con != null) {
			try {
				con.close();
			} catch (SQLException e) {
			}
		}
		
		}
	
		System.out.println("MultipleResult"+result);
		return result;
		}

	@Override
	public Conference getConferenceData(String conferenceId) throws BusinessException {
		// TODO Auto-generated method stub
	   
	   	//System.out.println(conferenceId);
	    String sql = "SELECT conference_id, name, conference_abstract, image, pdf, city, date, latitude, longitude FROM conferences WHERE conference_id='"+conferenceId+"'";
	    LOGGER.info(sql);
	    
		//List<Conference> result = new ArrayList<>();
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		
		
		Conference preview = new Conference();
		try {
		con = dataSource.getConnection();
		st = con.createStatement();
		
		rs = st.executeQuery(sql);
		
		
		while (rs.next()) {
			
			preview.setName(rs.getString("name"));
			preview.setConferenceAbstract(rs.getString("conference_abstract"));
			preview.setImage(rs.getString("image"));
			preview.setPdf(rs.getString("pdf"));
			preview.setCity(rs.getString("city"));
			preview.setDate(rs.getDate("date"));
			preview.setLatitude(rs.getDouble("latitude"));
			preview.setLongitude(rs.getDouble("longitude"));
			
		}
	
		} catch (SQLException e) {
		e.printStackTrace();
		throw new BusinessException(e);
		} finally {
		if (st != null) {
			try {
				st.close();
			} catch (SQLException e) {
			}
		}
		if (con != null) {
			try {
				con.close();
			} catch (SQLException e) {
			}
		}
		
		}
	
		return preview;
		}
	

//	@Override
//	public List<Parking> getParking(Double lat, Double lon, Integer vicinityRange) throws BusinessException {
//		double latRadians = Math.toRadians(lat);
//		double lonRadians = Math.toRadians(lon);
//		// convert m to km
//		double vicinityRangeToKM = (double) vicinityRange / 1000;
//	    // first-cut bounding box (in degrees)
//	    double maxLat = lat + Math.toDegrees(vicinityRangeToKM/EARTH_RADIUS);
//	    double minLat = lat - Math.toDegrees(vicinityRangeToKM/EARTH_RADIUS);
//	    double maxLon = lon + Math.toDegrees(Math.asin(vicinityRangeToKM/EARTH_RADIUS) / Math.cos(Math.toRadians(lat)));
//	    double minLon = lon - Math.toDegrees(Math.asin(vicinityRangeToKM/EARTH_RADIUS) / Math.cos(Math.toRadians(lat)));
//
//	    String sql = "SELECT parking_id, name, latitude, longitude, capacity, capacity_disabled, fee, " +
//	                 "  acos(sin(" + latRadians + ")*sin(radians(latitude)) + cos(" + latRadians + ")*cos(radians(latitude))*cos(radians(longitude)-" + lonRadians + ")) * " + EARTH_RADIUS + " As D " +
//	            " FROM ( " +
//	            "    SELECT *" +
//	            "    FROM parkings" +
//	            "    WHERE latitude Between " + minLat+ " And " + maxLat + 
//	            "      And longitude Between " + minLon + " And " + maxLon +
//	            ") As FirstCut " +
//	            " WHERE acos(sin(" + latRadians+ ")*sin(radians(latitude)) + cos(" + latRadians + ")*cos(radians(latitude))*cos(radians(longitude)-" + lonRadians+ ")) * " + EARTH_RADIUS + " < " + vicinityRangeToKM +
//	            " ORDER by name";
//		LOGGER.info(sql);
//	    //String sql = "SELECT * FROM parkings p ORDER BY p.name";
//	    
//		List<Parking> result = new ArrayList<>();
//		Connection con = null;
//		Statement st = null;
//		ResultSet rs = null;
//		try {
//			con = dataSource.getConnection();
//			st = con.createStatement();
//
//			rs = st.executeQuery(sql);
//			while (rs.next()) {
//				Parking parking = new Parking();
//				parking.setId(rs.getLong("parking_id"));
//				parking.setName(rs.getString("name"));
//				parking.setLatitude(rs.getDouble("latitude"));
//				parking.setLongitude(rs.getDouble("longitude"));
//				parking.setCapacity(rs.getInt("capacity"));
//				parking.setCapacityDisabled(rs.getInt("capacity_disabled"));
//				parking.setFee(rs.getDouble("fee"));
//				result.add(parking);
//			}
//
//		} catch (SQLException e) {
//			e.printStackTrace();
//			throw new BusinessException(e);
//		} finally {
//			if (st != null) {
//				try {
//					st.close();
//				} catch (SQLException e) {
//				}
//			}
//			if (con != null) {
//				try {
//					con.close();
//				} catch (SQLException e) {
//				}
//			}
//
//		}
//		
//		return result;
//	}

}
