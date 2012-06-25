package br.com.google.android.services;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

import android.content.Context;
import android.util.Log;
import br.com.google.android.pojos.Car;
import br.com.google.android.utils.ConexaoUtil;
import br.com.google.android.utils.XMLUtils;

public class CarService {
	private static final String URL = "http://www.livroandroid.com.br/livro/carros/carros_{tipo}.xml";
	private static final String TAG = "CarService";
	
	public static List<Car> getCars(Context context, String type) {
		String url = URL.replace("{tipo}", type);
		String xml = null;
		xml = ConexaoUtil.connect(url, "UTF-8");
		return parserXML(context, xml);
	}

	private static List<Car> parserXML(Context context, String xml) {
		List<Car> cars = new ArrayList<Car>();
		Element root = XMLUtils.getRoot(xml, "UTF-8");
		
		List<Node> nodes = XMLUtils.getChildren(root, "carro");
		
		Car car = null;
		for (Node node : nodes) {
			car = new Car();
			
			car.setName(XMLUtils.getText(node, "nome"));
			car.setDescription(XMLUtils.getText(node, "desc"));
			car.setUrlPicture(XMLUtils.getText(node, "url_foto"));
			car.setUrlInfo(XMLUtils.getText(node, "url_info"));
			
			Log.d(TAG, "Car: " + car.getName());
			
			cars.add(car);
		}
		return cars;
	}
}