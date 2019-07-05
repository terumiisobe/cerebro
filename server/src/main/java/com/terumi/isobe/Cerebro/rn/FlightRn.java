package com.terumi.isobe.Cerebro.rn;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import com.terumi.isobe.Cerebro.api.FlightApi;
import com.terumi.isobe.Cerebro.dao.FlightDao;
import com.terumi.isobe.Cerebro.model.Flight;

public class FlightRn {

	@Inject
	private FlightDao flightDao;

	public List<FlightApi> listar() {
		return this.converterListaParaApi(flightDao.listar());
	}

	public FlightApi converterParaApi(Flight entidade) {
		FlightApi api = new FlightApi();
		api.setId(entidade.getId());
		api.setOrigem(entidade.getOrigem());
		api.setDestino(entidade.getDestino());
		api.setData(entidade.getData());
		api.setVagas(entidade.getVagas());

		return api;
	}

	private List<FlightApi> converterListaParaApi(List<Flight> entidades) {
		List<FlightApi> apis = new ArrayList<FlightApi>();

		for (Flight entidade : entidades) {
			apis.add(this.converterParaApi(entidade));
		}

		return apis;
	}
}
