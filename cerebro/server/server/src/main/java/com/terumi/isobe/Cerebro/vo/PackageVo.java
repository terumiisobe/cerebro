package com.terumi.isobe.Cerebro.vo;

import com.terumi.isobe.Cerebro.model.Accommodation;
import com.terumi.isobe.Cerebro.model.Flight;

public class PackageVo {

	private AirfareVo passagem;
	private Accommodation hospedagem;

	public PackageVo(Flight ida, Flight volta, Accommodation hospedagem) {
		this.passagem = new AirfareVo(ida, volta);
		this.hospedagem = hospedagem;
	}

	public AirfareVo getPassagem() {
		return passagem;
	}

	public void setPassagem(AirfareVo passagem) {
		this.passagem = passagem;
	}

	public Accommodation getHospedagem() {
		return hospedagem;
	}

	public void setHospedagem(Accommodation hospedagem) {
		this.hospedagem = hospedagem;
	}

}
