/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import junit.framework.TestCase;

/**
 * Performs Validation Test for url validations.
 *
 * @version $Revision: 1128446 $ $Date: 2011-05-27 13:29:27 -0700 (Fri, 27 May
 *          2011) $
 */
public class UrlValidatorTest extends TestCase {

	private boolean printStatus = false;
	private boolean printIndex = false;// print index that indicates current scheme,host,port,path, query test were
										// using.

	public UrlValidatorTest(String testName) {
		super(testName);
	}

	public static void testManualTest() {
		UrlValidator urlVal = new UrlValidator(null, null, UrlValidator.ALLOW_ALL_SCHEMES);
		// System.out.println(urlVal.isValid("http://www.amazon.com"));

		System.out.println("------------------------- MANUALLY TESTING URLS -----------------------\n");

		System.out.println("TEST 1: PROTOCOL");

		// manual test: protocol
		System.out.println("URL: \"https://www.amazon.com\"");
		System.out.println("Expected: true, Actual: " + urlVal.isValid("https://www.amazon.com"));
		System.out.println("\nURL: \"ftp://www.amazon.com\"");
		System.out.println("Expected: true, Actual: " + urlVal.isValid("ftp://www.amazon.com"));
		System.out.println("\nURL: \":www.amazon.com\"");
		System.out.println("Expected: false, Actual: " + urlVal.isValid(":www.amazon.com"));
		System.out.println("\nURL: \"100e://www.amazon.com\"");
		System.out.println("Expected: false, Actual: " + urlVal.isValid("100e://www.amazon.com"));
		System.out.println("\nURL: \"asdf://www.amazon.com\"");
		System.out.println("Expected: true, Actual: " + urlVal.isValid("asdf://www.amazon.com"));
		System.out.println("\nURL: \"www.amazon.com\"");
		System.out.println("Expected: false, Actual: " + urlVal.isValid("www.amazon.com"));

		System.out.println("\nTEST 2: AUTHORITY");

		// manual test: authority 
		System.out.println("\nURL: \"http://www.google.com\"");
		System.out.println("Expected: True, Actual: " + urlVal.isValid("http://www.google.com/"));
		System.out.println("\nURL: \"http://www.thisisaurl.com\""); 
		System.out.println("Expected: True, Actual: " + urlVal.isValid("http://www.thisisaurl.com/")); 
		System.out.println("\nURL: \"http://.amazon.com\"");
		System.out.println("Expected: False, Actual: " + urlVal.isValid("http://.amazon.com"));
		System.out.println("\nURL: \"\"");
		System.out.println("Expected: False, Actual: " + urlVal.isValid(""));
		System.out.println("\nURL: \"http://256.295.153.25\"");
		System.out.println("Expected: False, Actual: " + urlVal.isValid("http://256.295.153.25"));
		System.out.println("\nURL: \"http://www..com\"");
		System.out.println("Expected: False, Actual: " + urlVal.isValid("http://www..com"));

		System.out.println("\nTEST 3: PATH/QUERY"); 
 
		// manual test: path/query  
		System.out.println("\nURL:\"https://www.amazon.com/b?&node=17285120011&=value\"");
		System.out.println("Expected: True, Actual: " + urlVal.isValid("https://www.amazon.com/b?&node=17285120011&=value"));
		System.out.println("\nURL: \"https://www.amazon.com/path\"");
		System.out.println("Expected: True, Actual: " + urlVal.isValid("https://www.amazon.com/path"));
		System.out.println("\nURL: \"https://www.google.com/\"\"\"");
		System.out.println("Expected: False, Actual: " + urlVal.isValid("https://www.google.com/\"\""));
		System.out.println("\nURL: \"http://www.amazon.com/<<>>\"");
		System.out.println("Expected: False, Actual: " + urlVal.isValid("http://www.amazon.com/]"));

		System.out.println("\nTEST 4: PORT");

		// manual test: port 
		System.out.println("\nURL: \"http://www.amazon.com:8080\""); 
		System.out.println("Expected: True, Actual: " + urlVal.isValid("http://www.amazon.com:8080"));
		System.out.println("\nURL: \"http://www.amazon.com:80\"");
		System.out.println("Expected: True, Actual: " + urlVal.isValid("http://www.amazon.com:80"));
		System.out.println("\nURL: \"http://www.amazon.com:k\"");
		System.out.println("Expected: False, Actual: " + urlVal.isValid("http://www.amazon.com:k"));
	}

	// main test function to test all input partitions
	public static void testAllPartitions() {

		System.out.println("\n------------------------- INPUT PARTITIONING TESTS -----------------------\n");

		UrlValidator urlVal = new UrlValidator(null, null, UrlValidator.ALLOW_ALL_SCHEMES);

		// input partition table scheme
		boolean[][] table_scheme = { { true, true, true, true }, { false, false, false, false },
				{ false, true, true, true }, { true, false, true, true }, { true, true, false, true },
				{ true, true, true, false }, { false, false, true, true }, { false, true, false, true },
				{ true, false, true, false }, { true, true, false, false }, { false, true, true, false },
				{ true, false, false, true }, { false, false, false, true }, { true, false, false, false },
				{ false, false, true, false }, { false, true, false, false } };

		// loop through boolean 2D array
		for (int i = 0; i < table_scheme.length; i++) {
			
			//print out table scheme header
			System.out.println("\n--------- INPUT PARTITION TEST SCHEME #" + i + ":\n Protocol Input: "
				+ table_scheme[i][0] + ", Authority Input: " + table_scheme[i][1] + ", Port Input: "
				+ table_scheme[i][2] + ", Query/Path Input: " + table_scheme[i][3] + "\n-------------- \n");
			
			// loop through partitions
			// changed loop to 3 to fix current build error bug
			for (int j = 0; j < 3; j++) {
				String test_url = testProtocolInput(table_scheme[i][0], j) + testAuthorityInput(table_scheme[i][1], j)
						+ testPortInput(table_scheme[i][2], j) + testPathInput(table_scheme[i][3], j);

				// get expected boolean for this URL
				boolean check_expected = table_scheme[i][0] && table_scheme[i][1] && table_scheme[i][2]
						&& table_scheme[i][3];

				// print out URL and expected/actual results
				System.out.println(test_url);
				System.out.println("Expected: " + check_expected + ", Actual: " + urlVal.isValid(test_url) + "\n");
			}

		}
	}

	// input:
	// boolean: returns a string from either a valid string array or invalid string
	// array
	// integer: index value of array
	// precondition: all partition array sizes must be the same length
	public static String testProtocolInput(boolean isValid, int index) {

		// array for valid protocols
		String[] valid = { "http://", "https://", "ftp://", "cid://", "mailto:", "" };

		// array for invalid protocols
		String[] invalid = { "h/t:", "http:/", "https:", "https/", ":::", "-&@" };

		if (isValid) {
			return valid[index];
		} else {
			return invalid[index];
		}

	}

	// Partition function to test invalid and valid authority with the
	// URLValidator() function provided by Apache Commons.
	public static String testAuthorityInput(boolean isValid, int index) {
		// Valid authority String array of size 6
		String[] valid = { "www.facebook.com", "amazon.com", "255.255.255.255", "facebook.cc", "facebook.au",
				"1.1.1.1" };

		// Invalid authority String array of size 6
		String[] invalid = { "www.website.invalid", "amazon.123", "300.300.300.300", "1.1.1.1.1", "abcdefghijklmnop",
				"" };

		// Returning string at current index of the valid or invalid authority to main
		// function.
		if (isValid) {
			return valid[index];
		} else {
			return invalid[index];
		}
	}

	public static String testPortInput(boolean isValid, int index) {

		// Arrays of valid and invalid ports
		String[] valid = { ":8080", ":65535", ":1", ":0", ":80", ":345"};
		String[] invalid = { ":notaport", ":10000000000", ":x", ":65536", "", ":#"};

		// Return valid or invalid port string at location within array specified,
		// depending on arguments
		if (isValid) {
			return valid[index];

		} else {
			return invalid[index];
		}
	}

	public static String testPathInput(boolean isValid, int index) {
		// Arrays for valid and invalid paths/queries
		String[] valid = { "/", "/a", "/?", "/path?query", "/page8887", ""};
		String[] invalid = { "/", "/<", "/>", "\"", "/ /", "/%"};

		// Return valid or invalid path string at location within array specified,
		// depending on arguments
		if (isValid) {
			return valid[index];
		} else {
			return invalid[index];
		}
	}

	// public void testYourSecondPartition() {

	//}

	//public void testIsValid() {
	//	for (int i = 0; i < 10000; i++) {

	//	}
	//}

	public void testAnyOtherUnitTest() {
		inetAddressUnitTest();
		URLValPortUnitTest();
		URLValQueryUnitTest();
		domainValidatorUnitTest(); 
	}

	/**
	 * Create set of tests by taking the testUrlXXX arrays and running through all
	 * possible permutations of their combinations.
	 *
	 * @param testObjects
	 *            Used to create a url.
	 */
	// Unit test for inetAddress segments
	// valid: all segments should return true between 0-255
	// invalid: all segments above 255 should return false
	public void inetAddressUnitTest() {

		// init new InetAddressValidator object
		InetAddressValidator inetValidator = new InetAddressValidator();

		// intialize inet address segments to 0
		int[] addressSegments = { 0, 0, 0, 0 };
		int i, j; // loop variables
		boolean isValidSegment;
		String inetAddress;

		System.out.println("--------- UNIT TEST FOR INET ADDRESS -----------\n");

		System.out.println("--------- Test 1: INET Address valid range 0-255 -----------\n");
		// test each segment between 0-255
		for (i = 0; i < 4; i++) {

			// test valid range
			for (j = 0; j < 255; j++) {

				addressSegments[i]++; // increment segment

				// create inet address string
				inetAddress = addressSegments[0] + "." + addressSegments[1] + "." + addressSegments[2] + "."
						+ addressSegments[3];

				isValidSegment = inetValidator.isValid(inetAddress);
				System.out.println(inetAddress);
				System.out.println("Expected: true, Actual: " + isValidSegment);
			}

		}

		System.out.println("\n--------- Test 2: INET Address invalid range > 255 -----------\n");
		// test each segment above 255
		for (i = 0; i < 4; i++) {

			// test invalid range
			for (j = 255; j < 300; j++) {

				addressSegments[i]++;
				inetAddress = addressSegments[0] + "." + addressSegments[1] + "." + addressSegments[2] + "."
						+ addressSegments[3];

				isValidSegment = inetValidator.isValid(inetAddress);
				System.out.println(inetAddress);
				System.out.println("Expected: false, Actual: " + isValidSegment);
			}

		}

	}
	
	//Unit test for Port Values, uses the URLValidator and the isValid() function to test each port.
	//Valid # Range: 1-65535
	//Invalid # Range: > 65535
	public void URLValPortUnitTest()
	{
		UrlValidator urlVal = new UrlValidator(null, null, UrlValidator.ALLOW_ALL_SCHEMES);
		//int used to increment port value
		int i;
		//boolean value used to check if URL is valid
		boolean Is_Val;
		//Valid url used to add port to
		String ValURL = "https://amazon.com:";
		String URLProt;
		
		//First loop runs in increments of 128 so we don't have to run 65536 different ports.
		System.out.println("\n\n--------- UNIT TEST FOR PORT IN URLVALIDATOR ---------\n");
		System.out.println("--------- Test 1: Valid Port range 0-65535 using increments of 128 ---------\n");
		for(i = 0; i <= 65536; i += 128)
		{
			URLProt = ValURL + i;
			//Holds valid URL + port number
			Is_Val = urlVal.isValid(URLProt);
			System.out.println(URLProt);
			System.out.println("Expected: true, Actual: " + Is_Val);
		}
		
		//Second loop runs for around 300 invalid port numbers to check and see if the UrlValidator is correctly checking invalid ports.
		System.out.println("\n\n--------- Test 2: Invalid Port range 65535-65800 using increments of 1 ---------\n");
		
		for(i = 65536; i <= 65800; i++)
		{
			URLProt = ValURL + i;
			//Holds valid URL + port number
			Is_Val = urlVal.isValid(URLProt);
			System.out.println(URLProt);
			System.out.println("Expected: false, Actual: " + Is_Val);
		}
		
	}
	
	//Tests an array of 11 valid Querys to see if they return valid or invalid
	//I have no idea if there is such thing as an invalid query from what I have read online it seems every ASCII character is valid.
	public void URLValQueryUnitTest()
	{
		//UrlValidator Object
		UrlValidator urlVal = new UrlValidator(null, null, UrlValidator.ALLOW_ALL_SCHEMES);
		Boolean Is_Val;
		//Valid URL used to test Querys
		String ValURL = "https://facebook.com";
		//Array of Querys to be tested
		String[] Querys = {"?action=view", "?action=edit&mode=up", "?act=hsdsdfhsd", "?====..a.dfsdsd``11~~~", "?~~``==IsThere", "?)(__+_SuchThing", "?@#$##AsAn", "?***&&&InvalidQuery??", "?::::12345::::IDont", "??????????know", ""};
		int i;
		//Holds the final URL with the Query added to the end.
		String URLQuery;
		System.out.println("\n\n--------- UNIT TEST FOR QUERY IN URLVALIDATOR ---------\n");
		System.out.println("--------- Test: Testing 10 Different Querys ---------\n");
		//Test all 11 Querys
		for(i=0; i <=10; i++)
		{
			//Combine Valid URL with each Query.
			URLQuery = ValURL + Querys[i];
			Is_Val = urlVal.isValid(URLQuery);
			System.out.println(URLQuery);
			//Compare the expected result to the actual result from isValid()
			System.out.println("Expected:true, Actual: " + Is_Val);
		}
	}

	/**
 	 * Creates arrays for valid and invalid: subdomains (i.e. www), domains (i.e. example), and tlds (i.e. com)
 	 * and tests combinations of all valid parts of a domain name, and all invalid parts of a domain name.  
 	 * 
 	 */ 	
	public void domainValidatorUnitTest() {
		// Create valid sub-domains (i.e. the 'www' in www.example.com)
		String[] valid_subdomain = { "www", "abc", "en", "123", "as23", "w-w", "a-", "thisisavalidsubdomain", "q", "asdfjklmnoasdfjklmnoasdfjklmnoasdfjklmnoasdfjklmnoasdfjklmnoasd" };
		int valid_subdomain_ct = 10; 
				
		// Create invalid sub-domains 
		String[] invalid_subdomain = { "-w-", "#", "?", "12&", ")", ">", "*", " ", "", "asdfjklmnoasdfjklmnoasdfjklmnoasdfjklmnoasdfjklmnoasdfjklmnoasde" };
		int invalid_subdomain_ct = 10; 
				
		// Create valid domain -- without TLD (i.e. example in example.com)
		String[] valid_domain = { "google", "amazon", "thinkgeek", "asdfjkl", "123", "abc", "91a", "", "a", "asdfjklmnoasdfjklmnoasdfjklmnoasdfjklmnoasdfjklmnoasdfjklmnoasd" };
		int valid_domain_ct = 10; 
				
		// Create invalid domain -- without TLD
		String[] invalid_domain = { "w-+", "#",	"?", "12&", ")", ">", "*", " ",	"", "asdfjklmnoasdfjklmnoasdfjklmnoasdfjklmnoasdfjklmnoasdfjklmnoasde" };
		int invalid_domain_ct = 10; 
			
		// Create valid TLDs - not a comprehensive list, but includes all Country Codes and all original TLDs
		String[] valid_tlds = { "com", "org", "net", "int", "edu", "gov", "mil", 
				"ac", "ad", "ae", "af", "ag", "ai", "al", "am", "an", "ao", "aq", "ar", "as", "at", "au", "aw", "ax", "az", "ba", "bb", "bd", "be", "bf", "bg", "bh", "bi",			
				"bj", "bl", "bn", "bo", "bq", "br", "bs", "bt", "bv", "bw", "by", "bz", "ca", "cc", "cd", "cf", "cg", "ch", "ci", "ck", "cl", "cm", "cn", "co", "cr", "cu", "cv", "cw", "cx", 		
				"cy", "cz", "de", "dj", "dk", "dm", "do", "dz", "ec", "ee", "eg", "eh", "er", "es", "et", "eu", "fi", "fj", "fk", "fm", "fo", "fr", "ga", "gb", "gd", "ge", "gf", "gg", "gh", 
				"gi", "gl", "gm", "gn", "gp", "gq", "gr", "gs", "gt", "gu", "gw", "gy", "hk", "hm", "hn", "hr", "ht", "hu", "id", "ie", "il", "im", "in", "io", "iq", "ir", "is", "it", "je", 
				"jm", "jo", "jp", "ke", "kg", "kh", "ki", "km", "kn", "kp", "kr", "kw", "ky", "kz", "la", "lb", "lc", "li", "lk", "lr", "ls", "lt", "lu", "lv", "ly", "ma", "mc", "md", "me", 
				"mf", "mg", "mh", "mk", "ml", "mm", "mn", "mo", "mp", "mq", "mr", "ms", "mt", "mu", "mv", "mw", "mx", "my", "mz", "na", "nc", "ne", "nf", "ng", "ni", "nl", "no", "np", "nr", 
				"nu", "nz", "om", "pa", "pe", "pf", "pg", "ph", "pk", "pl", "pm", "pn", "pr", "ps", "pt", "pw", "py", "qa", "re", "ro", "rs", "ru", "rw", "sa", "sb", "sc", "sd", "se", "sg", 
				"sh", "si", "sj", "sk", "sl", "sm", "sn", "so", "sr", "ss", "st", "su", "sv", "sx", "sy", "sz", "tc", "td", "tf", "tg", "th", "tj", "tk", "tl", "tm", "tn", "to", "tp", "tr", 
				"tt", "tv", "tw", "tz", "ua", "ug", "uk", "um", "us", "uy", "uz", "va", "vc", "ve", "vg", "vi", "vn", "vu", "wf", "ws", "ye", "yt", "za", "zm", "zw" };							 	
		int valid_tlds_ct = 261; 
			
		// Create invalid TLDs 
		String[] invalid_tlds = { "aa", "ab", "le", "ld", "lo", "ll", "lm", "ln", "lp", "lq" };	// Invalid Country Codes
		int invalid_tlds_ct = 10; 
		
		// Make a new instance of UrlValidator class
		UrlValidator url_validator = new UrlValidator(null, null, UrlValidator.ALLOW_ALL_SCHEMES);
		
		// TEST VALID URLS
		for (int i = 0; i < valid_subdomain_ct; i++) {
			for (int j = 0; j < valid_domain_ct; j++) {
				for (int k = 0; k < valid_tlds_ct; k++) {
					String test_url = "http://" + valid_subdomain[i] + "." + valid_domain[j] + "." + valid_tlds[k]; 
					System.out.println(test_url);
					System.out.println("Expected: true, Actual: " + url_validator.isValid(test_url)); 
				}
			}
		}
		
		// TEST INVALID URLS
		for (int i = 0; i < invalid_subdomain_ct; i++) {
			for (int j = 0; j < invalid_domain_ct; j++) {
				for (int k = 0; k < invalid_tlds_ct; k++) {
					String test_url = "http://" + invalid_subdomain[i] + "." + invalid_domain[j] + "." + invalid_tlds[k]; 
					System.out.println(test_url);
					System.out.println("Expected: false, Actual: " + url_validator.isValid(test_url)); 
				}
			}
		}
	}
}
