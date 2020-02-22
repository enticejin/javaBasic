package com.uwbhome.pm.observer;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import org.springframework.web.socket.TextMessage;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.uwbhome.pm.utils.PageData;
import com.uwbhome.pm.websocket.SystemWebSocketHandler;
import com.uwbhome.rtle.api.RTLEAPI;
import com.uwbhome.rtle.api.Tag;
import com.uwbhome.rtle.comm.LonLatPoint;
import com.uwbhome.rtle.comm.Point;
import com.uwbhome.rtle.comm.Point2;
import com.uwbhome.rtle.event.RTLEEvent_TagLocated;
import com.uwbhome.rtle.utils.Misc;
/**
 * 用于处理接收到定位信息后的处理
 * @author Administrator
 *
 */
public class LocationObserver implements Observer {
	public LocationObserver() {
		super();
	}
	@Override
	public void update(Observable o, Object arg) {
		//坐标定位信息
		if(arg instanceof RTLEEvent_TagLocated){
			
			RTLEEvent_TagLocated rtag=(RTLEEvent_TagLocated)arg;
			
			List<PageData> tagList = new ArrayList<PageData>();
			LonLatPoint origin = new LonLatPoint(RTLEAPI.getInstance().getOrigin().getLongitude(), RTLEAPI.getInstance().getOrigin().getLatitude(), 0);
			//Tag ttag=(Tag)arg;
	//System.out.println(ttag.getX()+","+ttag.getY()+","+ttag.getZ());
			PageData p = new PageData();
			LonLatPoint lonLatPoint = Misc.meter2LonLat(origin, rtag.getX(), rtag.getY());
			Double lon = Double.valueOf(Misc.fixedWidthDoubletoString(lonLatPoint.getLongitude(), 8));
			Double lat = Double.valueOf(Misc.fixedWidthDoubletoString(lonLatPoint.getLatitude(), 8));
			p.put("X",rtag.getX());
			p.put("Y",rtag.getY());
			p.put("Z",rtag.getZ());
			p.put("lat",lat);
			p.put("lon",lon);
			p.put("TAG_ID",rtag.getId());
			p.put("TAG_SEQ64",(rtag.getLocateSeq64()));
			p.put("switchStatus",rtag.getSwitchStatus());
			//if(ttag.getAreaId() != null && !"".equals(ttag.getAreaId())){
			if(rtag.getAreaId() != null && !"".equals(rtag.getAreaId())){
				//p.put("TAG_NAME", ttag.getName()+"->"+ttag.getSeq64()+"-->"+Areas.getAreaName(ttag.getOutPutCoordareaId()));
				//p.put("TAG_NAME", ttag.getId()+"->"+ttag.getSeq64()+"-->"+Areas.getAreaName(ttag.getOutPutCoordareaId()));
				p.put("TAG_NAME", rtag.getId()+"->"+(rtag.getLocateSeq64())+"-->"+rtag.getAreaId()+"-->("+rtag.getX()+","+rtag.getY()+","+rtag.getZ()+")");
			}else{
				p.put("TAG_NAME", rtag.getId()+"->"+(rtag.getLocateSeq64()));
			}
			LonLatPoint lonLatCorPoint = Misc.meter2LonLat(origin, rtag.getX(), rtag.getY());
			Double lonCor = Double.valueOf(Misc.fixedWidthDoubletoString(lonLatCorPoint.getLongitude(), 8));
			Double latCor = Double.valueOf(Misc.fixedWidthDoubletoString(lonLatCorPoint.getLatitude(), 8));
			p.put("latCor", latCor);
			p.put("lonCor", lonCor);

			//读取原始X,Y
			//轨迹
			/*
			 * Point[] points = rtag.getRecentLocations();// List<Double[]> coords = new
			 * ArrayList<Double[]>(); if(null == points) { points = new Point[0]; } for(int
			 * i=0;i<10;i++) { Point piont = points[i]; LonLatPoint lonLatPoint2 =
			 * Misc.meter2LonLat(origin, piont.getX(), piont.getY()); Double lon2 =
			 * Double.valueOf(Misc.fixedWidthDoubletoString(lonLatPoint2.getLongitude(),
			 * 8)); Double lat2 =
			 * Double.valueOf(Misc.fixedWidthDoubletoString(lonLatPoint2.getLatitude(), 8));
			 * Double[] coord = new Double[2]; coord[0] = lon2; coord[1] = lat2;
			 * coords.add(coord); } p.put("coords",coords);
			 */

			Point2 p2=new Point2(rtag.getX(),rtag.getY());
			//查看当前坐标与围栏的情况
			//boolean fenceState=CheckElectronicFence(p2);
			//p.put("fenceState", fenceState);
			//if("0008DEFFFE000153".equals(ttag.getId()))
			{
				tagList.add(p);
			}

			ObjectMapper mapper = new ObjectMapper();
			TextMessage msg;
			try {
				msg = new TextMessage(mapper.writeValueAsString(tagList));
				msg.asBytes();
				//使用socket输出到客户端
		    	SystemWebSocketHandler.getInstance().sendMessageToUsers(msg);
			} catch (JsonProcessingException e) {

				e.printStackTrace();
			}
		}
	}
	/*
	//判断标签电子围栏状态
	public boolean CheckElectronicFence(Point2 p)
	{
		boolean fenceState=false;
		//建立电子围栏的时候，应该取得围栏需要监控哪些标签，
		for (Map<String,Object> map :Const.fencenetList) {
			//String fenceId=map.get("FENCENET_ID").toString();
			List<Point2> pointlist=(List<Point2>)map.get("point2List");
			Point2[] p2=new Point2[pointlist.size()];
			for (int i=0;i<pointlist.size();i++) {
				p2[i]=pointlist.get(i);
			}
			Polygon polygonSharp=new Polygon(p2);
			//String triggerMsg="";
			if(polygonSharp.contains(p))
			{
				//triggerMsg="标签进入围栏，围栏ID："+fenceId;
				//说明点在围栏内
				//System.out.println(triggerMsg);
				fenceState=true;
				break;

			}
		}
		return fenceState;
	}

	//检查多边形是否包含一个点
	public boolean CheckPolygonContansPoint(Point2 p,Point2[] points)
	{
			//检查这个点是否在一个区域里
			Polygon poloy=new Polygon(points);
			boolean ret=poloy.contains(p);
			return ret;
	}
*/

}
