package br.com.caelum.estoque.modelo.usuario;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class DateAdapter extends XmlAdapter<String, Date> {
	private String pattern = "dd/MM/yyyy";

	@Override
	public String marshal(Date v) throws Exception {
		return new SimpleDateFormat(this.pattern).format(v);
	}

	@Override
	public Date unmarshal(String v) throws Exception {
		return new SimpleDateFormat(this.pattern).parse(v);
	}

}
