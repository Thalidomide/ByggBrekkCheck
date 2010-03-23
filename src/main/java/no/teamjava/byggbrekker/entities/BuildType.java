package no.teamjava.byggbrekker.entities;

/**
 * @author Olav Jensen
 * @since 21.okt.2009
 */
public enum BuildType {

	DEFAULT("DSB-DEF", "Default",  BuildCategory.IMPORTANT),
	BRANCH_ST3("DSB-ST3", "Branch Prod3_x", BuildCategory.IMPORTANT),
	BRANCH_ST4("DSB-ST4", "Branch ST4", BuildCategory.IMPORTANT),
	DEPLOY_SNAPSHOT("DSB-DEPSNAP", "Deploy snapshot", BuildCategory.IMPORTANT),
	SMITHERWICK("DSB-SYSTESTSMW", "Deploy Smitherwick", BuildCategory.IMPORTANT),
	DEPLOY_SYSTEST("DSB-DEPSYS", "Deploy systest", BuildCategory.IMPORTANT),
	INTEGRATION_TESTS("DSB-EXTIT", "Integration tests", BuildCategory.MINOR),
	PRODUKSJONS_BYGG("DSB-PROD", "Produksjon bygg", BuildCategory.MINOR),
	MAVEN_SITE("DSB-MVNSITE", "Maven site", BuildCategory.MINOR);

	private final static String lookupPrefix = "<a href=\"/byggmesterbob/browse/";
	private final static String lookupPostfix = "/log\" class=";

	private final String lookup;
	private String text;
	private BuildCategory category;

	private BuildType(String lookup, String text, BuildCategory category) {
		this.category = category;
		this.text = text;
		this.lookup = lookup;
	}

	public String getLookup() {
		return lookupPrefix + lookup + lookupPostfix;
	}

	public String getText() {
		return text;
	}

	public BuildCategory getCategory() {
		return category;
	}
}
