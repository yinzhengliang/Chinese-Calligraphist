package domain;

import java.util.List;

public final class DomainList {
	private String domainListLoadingPath = "";

	private DomainDefinition m_domains;

	private List<String> shapeNames;

	private List<DomainShape> m_shapes;
	
	public static void main(String[] args) {
		DomainShape test = new DomainShape("C:/Users/Yin/workspace/Chinese-Calligraphist/resources/ShapeDefinition/Character/SanDianShui/SanDianShui.xml");
		System.out.println("!!!");
	}

}
