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
/**
 * Performs Validation Test for url validations.
 * 
 */

import junit.framework.TestCase;

/**
 * Final Project: Part B
 * CS 362
 * 12/4/2017
 * Group Members:
 * 	David Asuncion (asunciod)
 * 	David Jansen (jansedav)
 * 	Natasha Kvavle (kvavlen)
 * 
 */
public class UrlValidatorTest extends TestCase {

	private boolean printStatus = false;
	private boolean printIndex = false;// print index that indicates current scheme,host,port,path, query test were
										// using.

	public UrlValidatorTest(String testName) {
		super(testName);
	}

	/**
	 * manual test function 
	 */
	public static void testManualTest() {
		UrlValidator urlVal = new UrlValidator(null, null, UrlValidator.ALLOW_ALL_SCHEMES);
		
		// Keep track of how many tests performed v. how many pass
		int result = 0; 
		int compare = 0; 
		
		System.out.println("------------------------- MANUALLY TESTING URLS -----------------------\n");

		System.out.println("TEST 1: PROTOCOL");

		// manual test: protocol
		System.out.println("URL: \"https://www.amazon.com\"");
		System.out.println("Expected: true, Actual: " + urlVal.isValid("https://www.amazon.com"));
		if (urlVal.isValid("https://www.amazon.com")) {
			result += 1; 
		}
		compare += 1;
		
		System.out.println("\nURL: \"ftp://www.amazon.com\"");
		System.out.println("Expected: true, Actual: " + urlVal.isValid("ftp://www.amazon.com"));
		if (urlVal.isValid("ftp://www.amazon.com")) {
			result += 1; 
		}
		compare += 1; 
		
		System.out.println("\nURL: \":www.amazon.com\"");
		System.out.println("Expected: false, Actual: " + urlVal.isValid(":www.amazon.com"));
		if (urlVal.isValid(":www.amazon.com") == false) {
			result += 1;
		}
		compare += 1; 
		
		System.out.println("\nURL: \"100e://www.amazon.com\"");
		System.out.println("Expected: false, Actual: " + urlVal.isValid("100e://www.amazon.com"));
		if(urlVal.isValid("100e://www.amazon.com") == false) {
			result += 1; 
		}
		compare += 1; 
		
		System.out.println("\nURL: \"asdf://www.amazon.com\"");
		System.out.println("Expected: true, Actual: " + urlVal.isValid("asdf://www.amazon.com"));
		if(urlVal.isValid("asdf://www.amazon.com")) {
			result += 1; 
		}
		compare += 1;
		
		System.out.println("\nURL: \"www.amazon.com\"");
		System.out.println("Expected: false, Actual: " + urlVal.isValid("www.amazon.com"));
		if (urlVal.isValid("www.amazon.com") == false) {
			result += 1; 
		}
		compare += 1;

		System.out.println("\nTEST 2: AUTHORITY");

		// manual test: authority 
		System.out.println("\nURL: \"http://www.google.com\"");
		System.out.println("Expected: True, Actual: " + urlVal.isValid("http://www.google.com/"));
		if (urlVal.isValid("http://www.google.com/")) {
			result += 1; 
		}
		compare += 1;
		
		System.out.println("\nURL: \"http://www.thisisaurl.com\""); 
		System.out.println("Expected: True, Actual: " + urlVal.isValid("http://www.thisisaurl.com/")); 
		if (urlVal.isValid("http://www.thisisaurl.com/")) {
			result += 1; 
		}
		compare += 1;
	
		System.out.println("\nURL: \"http://.amazon.com\"");
		System.out.println("Expected: False, Actual: " + urlVal.isValid("http://.amazon.com"));
		if (urlVal.isValid("http://.amazon.com") == false) {
			result += 1; 
		}
		compare += 1;
		
		System.out.println("\nURL: \"\"");
		System.out.println("Expected: False, Actual: " + urlVal.isValid(""));
		if (urlVal.isValid("") == false) {
			result += 1; 
		}
		compare += 1;
		
		System.out.println("\nURL: \"http://256.295.153.25\"");
		System.out.println("Expected: False, Actual: " + urlVal.isValid("http://256.295.153.25"));
		if (urlVal.isValid("http://256.295.153.25") == false) {
			result += 1; 
		}
		compare += 1;
		
		System.out.println("\nURL: \"http://www..com\"");
		System.out.println("Expected: False, Actual: " + urlVal.isValid("http://www..com"));
		if(urlVal.isValid("http://www..com") == false) {
			result += 1;
		}
		compare += 1;
		
		System.out.println("\nTEST 3: PATH/QUERY"); 
 
		// manual test: path/query  
		System.out.println("\nURL:\"https://www.amazon.com/b?&node=17285120011&=value\"");
		System.out.println("Expected: True, Actual: " + urlVal.isValid("https://www.amazon.com/b?&node=17285120011&=value"));
		if(urlVal.isValid("https://www.amazon.com/b?&node=17285120011&=value")) {
			result += 1; 
		}
		compare += 1; 
		
		System.out.println("\nURL: \"https://www.amazon.com/path\"");
		System.out.println("Expected: True, Actual: " + urlVal.isValid("https://www.amazon.com/path"));
		if(urlVal.isValid("https://www.amazon.com/path")) {
			result += 1; 
		}
		compare += 1; 
		
		System.out.println("\nURL: \"https://www.google.com/\"\"\"");
		System.out.println("Expected: False, Actual: " + urlVal.isValid("https://www.google.com/\"\""));
		if(urlVal.isValid("https://www.google.com/\"\"") == false) {
			result += 1; 
		}
		compare += 1; 
		
		System.out.println("\nURL: \"http://www.amazon.com/<<>>\"");
		System.out.println("Expected: False, Actual: " + urlVal.isValid("http://www.amazon.com/]"));
		if (urlVal.isValid("http://www.amazon.com/]") == false) {
			result += 1; 
		}
		compare += 1; 

		System.out.println("\nTEST 4: PORT");

		// manual test: port 
		System.out.println("\nURL: \"http://www.amazon.com:8080\""); 
		System.out.println("Expected: True, Actual: " + urlVal.isValid("http://www.amazon.com:8080"));
		if(urlVal.isValid("http://www.amazon.com:8080")) {
			result += 1;
		}
		compare += 1; 
		
		System.out.println("\nURL: \"http://www.amazon.com:80\"");
		System.out.println("Expected: True, Actual: " + urlVal.isValid("http://www.amazon.com:80"));
		if(urlVal.isValid("http://www.amazon.com:80")) {
			result += 1; 
		}
		compare += 1; 
		
		System.out.println("\nURL: \"http://www.amazon.com:k\"");
		System.out.println("Expected: False, Actual: " + urlVal.isValid("http://www.amazon.com:k"));
		if (urlVal.isValid("http://www.amazon.com:k") == false) {
			result += 1; 
		}
		compare += 1; 
		
		// Compare actual v. expected result
		assertTrue(compare == result); 
	}

	/**
	 *  main test function to test all input partitions
	 */
	public static void testAllPartitions() {

		System.out.println("\n------------------------- INPUT PARTITIONING TESTS -----------------------\n");
		
		// Keep track of actual v. expected number of passing tests
		int result = 0; 
		int compare = 0; 
		
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
				
				if(check_expected == true) {
					result += 1; 
				}
				compare += 1; 
			}
		}
		
		// Compare actual v. expected results
		assertTrue(result == compare); 
	}

	/**
 	 * Allows retrieval of valid or invalid protocol of a URL
 	 *  input: 
 	 *  	boolean: returns a string from either a valid string array or invalid string
 	 * 	 	array
 	 *  	integer: index value of array
 	 *  	precondition: all partition array sizes must be the same length
 	 */ 
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

	/**
 	 * Allows retrieval of valid or invalid authority of a URL
 	 * URLValidator() function provided by Apache Commons.
 	 * input: 
 	 *  	boolean: returns a string from either a valid string array or invalid string
 	 * 	 	array
 	 *  	integer: index value of array
 	 *  	precondition: all partition array sizes must be the same length
 	 */ 
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

	/**
 	 * Allows retrieval of valid or invalid port of a URL
 	 *  input: 
 	 *  	boolean: returns a string from either a valid string array or invalid string
 	 * 	 	array
 	 *  	integer: index value of array
 	 *  	precondition: all partition array sizes must be the same length
 	 */ 
	public static String testPortInput(boolean isValid, int index) {

		// Arrays of valid and invalid ports
		String[] valid = { ":8080", ":65535", ":1", ":0", ":80", ":345"};
		String[] invalid = { ":notaport", ":10000000000", ":x", ":65536", ":", ":#"};

		// Return valid or invalid port string at location within array specified,
		// depending on arguments
		if (isValid) {
			return valid[index];

		} else {
			return invalid[index];
		}
	}

	/**
 	 * Allows retrieval of valid or invalid path (and query) of a URL
 	 *  input: 
 	 *  	boolean: returns a string from either a valid string array or invalid string
 	 * 	 	array
 	 *  	integer: index value of array
 	 *  	precondition: all partition array sizes must be the same length
 	 */ 
	public static String testPathInput(boolean isValid, int index) {
		// Arrays for valid and invalid paths/queries
		String[] valid = { "/", "/a", "/?", "/path?query", "/page8887", ""};
		String[] invalid = { "/<", "/<<", "/>", "\"", "/ /", "/%"};

		// Return valid or invalid path string at location within array specified,
		// depending on arguments
		if (isValid) {
			return valid[index];
		} else {
			return invalid[index];
		}
	}

	/**
	 * Runs unit tests
	 * Asserts that all unit tests pass
	 */ 
	public void testAnyOtherUnitTest() {
		// Compare result (number of "passes" in unit tests) to the number of tests run 
		// Allows us to go through all tests without exiting program
		int result = 0; 
		int compare = 4; 
		result += inetAddressUnitTest();
		result += domainValidatorUnitTest(); 
		result += URLValPortUnitTest();
		result += URLValQueryUnitTest();

		assertTrue(compare == result); 
	}

	/**
	 * Unit test for inetAddress segments
	 * valid: all segments should return true between 0-255
	 * invalid: all segments above 255 should return false
	 *
	 * Return 1 if all tests pass, 0 otherwise
	 */ 
	public int inetAddressUnitTest() {

		// Keep track of how many tests pass v. expected number
		int result = 0; 
		int compare = 0; 
		
		// init new UrlValidator object
		UrlValidator urlValidator = new UrlValidator(null, null, UrlValidator.ALLOW_ALL_SCHEMES);

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
				inetAddress = "http://" + addressSegments[0] + "." + addressSegments[1] + "." + addressSegments[2] + "."
						+ addressSegments[3];

				isValidSegment = urlValidator.isValid(inetAddress);
				System.out.println(inetAddress);
				System.out.println("Expected: true, Actual: " + isValidSegment);
				if (isValidSegment) {
					result += 1; 
				}
				compare += 1; 
			}

		}

		System.out.println("\n--------- Test 2: INET Address invalid range > 255 -----------\n");
		// test each segment above 255
		for (i = 0; i < 4; i++) {

			// test invalid range
			for (j = 255; j < 300; j++) {

				addressSegments[i]++;
				inetAddress = "http://" + addressSegments[0] + "." + addressSegments[1] + "." + addressSegments[2] + "."
						+ addressSegments[3];

				isValidSegment = urlValidator.isValid(inetAddress);
				System.out.println(inetAddress);
				System.out.println("Expected: false, Actual: " + isValidSegment);
				if (isValidSegment == false) {
					result += 1; 
				}
				compare += 1; 
			}

		}
		
		
		// Return 1 if all tests pass, 0 otherwise
		if(compare == result) {
			return 1; 
		}
		else {
			return 0; 
		}
	}
	
	
	/**
	 * Unit test for Port Values, uses the URLValidator and the isValid() function to test each port.
 	 * Valid # Range: 1-65535
 	 * Invalid # Range: > 65535
 	 * 
 	 * Returns 1 if all tests pass, 0 otherwise
 	 */ 
	public int URLValPortUnitTest()
	{
		// Keep track of how many tests pass v. expected number
		int result = 0; 
		int compare = 0; 
		
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
			if (Is_Val) {
				result += 1; 
			}
			compare += 1; 
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
			if (!Is_Val) {
				result += 1; 
			}
			compare += 1; 
		}
		//Third Loop runs for the range in which the error is ocurring so I can check where the error is happening
		System.out.println("\n\n--------- Test 3: Checking the port range of 880-1024 where and error is occurring ---------");
		for(i = 880; i <= 1024; i++)
		{
			URLProt = ValURL + i;
			//Holds valid URL + port number
			Is_Val = urlVal.isValid(URLProt);
			System.out.println(URLProt);
			System.out.println("Expected: false, Actual: " + Is_Val);
			if (!Is_Val) {
				result += 1; 
			}
			compare += 1; 
		}
		
		// Return 1 if all pass, 0 otherwise
		if(compare == result) {
			return 1; 
		}
		else {
			return 0; 
		}
	}
	
	
	/**
 	 * Tests an array of 13 valid Querys to see if they return valid or invalid
 	 * I have no idea if there is such thing as an invalid query from what I have read online (see references in pdf) it seems every ASCII character is valid.
 	 * 
 	 * Returns 1 if all tests pass, 0 otherwise
 	 */ 
	public int URLValQueryUnitTest()
	{
		// Keep track of number of passed tests v. expected number
		int result = 0; 
		int compare = 0; 
		
		//UrlValidator Object
		UrlValidator urlVal = new UrlValidator(null, null, UrlValidator.ALLOW_ALL_SCHEMES);
		Boolean Is_Val;
		//Valid URL used to test Querys
		String ValURL = "https://facebook.com";
		//Array of Querys to be tested
		String[] Querys = {"?action=view", "?action=edit&mode=up", "?act=hsdsdfhsd", "?====..a.dfsdsd``11~~~", "?~~``==Is", "?)(__+_Thing", "?@#$##AsAn", "?***&&&Query??", "?::::12345::::ABC", "??????????know", "?", "?<", "?>"};
		String[] InvQuerys = {"action=view", "abc", "ABCDEF", "1123445", "<<<>>>>", "(~_~)" ," %$#%$", "AB@#tg23", "amazon", "**what", "Programmmmmm", "{}{}{}", "IB*()%%@!!+<><12"};
		int i;
		//Holds the final URL with the Query added to the end.
		String URLQuery;
		System.out.println("\n\n--------- UNIT TEST FOR QUERY IN URLVALIDATOR ---------\n");
		System.out.println("--------- Test1: Testing 13 Different Valid Querys ---------\n");
		//Test all 11 Querys
		for(i=0; i <=13; i++)
		{
			//Combine Valid URL with each Query.
			URLQuery = ValURL + Querys[i];
			Is_Val = urlVal.isValid(URLQuery);
			System.out.println(URLQuery);
			//Compare the expected result to the actual result from isValid()
			System.out.println("Expected:true, Actual: " + Is_Val);
			if (Is_Val) {
				result += 1; 
			}
			compare += 1; 
		}
		System.out.println("\n--------- Test2: Testing 13 Different Invalid Querys ---------\n");
		for(i=0; i <=12; i++)
		{
			//Combine Valid URL with each Invalid Query.
			URLQuery = ValURL + InvQuerys[i];
			Is_Val = urlVal.isValid(URLQuery);
			System.out.println(URLQuery);
			//Compare the expected result to the actual result from isValid()
			System.out.println("Expected:false, Actual: " + Is_Val);
			if (!Is_Val) {
				result += 1; 
			}
			compare += 1; 
		}
		
		// Return 1 if all pass, 0 otherwise
		if(compare == result) {
			return 1; 
		}
		else {
			return 0; 
		}
	}

	/**
 	 * Creates arrays for valid and invalid: subdomains (i.e. www), domains (i.e. example), and tlds (i.e. com)
 	 * and tests combinations of all valid parts of a domain name, and all invalid parts of a domain name.  
 	 * 
 	 * Returns 1 if all tests pass, 0 otherwise
 	 */ 	
	public int domainValidatorUnitTest() {
		// Keep track of number of tests passed v. expected
		int result = 0; 
		int compare = 0; 
		
		System.out.println("\n\n--------- UNIT TEST FOR DOMAIN ---------\n");
		
		// Create valid sub-domains (i.e. the 'www' in www.example.com)
		String[] valid_subdomain = { "www", "abc", "en", "123", "as23", "w-w", "a-", "thisisavalidsubdomain", "q", "asdfjklmnoasdfjklmnoasdfjklmnoasdfjklmnoasdfjklmnoasdfjklmnoasd" };
		int valid_subdomain_ct = 10; 
				
		// Create invalid sub-domains 
		String[] invalid_subdomain = { "-w-", "#", "?", "12&", ")", ">", "*", " ", "", "asdfjklmnoasdfjklmnoasdfjklmnoasdfjklmnoasdfjklmnoasdfjklmnoasde" };
		int invalid_subdomain_ct = 10; 
				
		// Create valid domain -- without TLD (i.e. example in example.com)
		String[] valid_domain = { "google", "amazon", "thinkgeek", "asdfjkl", "123", "abc", "91a", "a", "asdfjklmnoasdfjklmnoasdfjklmnoasdfjklmnoasdfjklmnoasdfjklmnoasd" };
		int valid_domain_ct = 9; 
				
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
		System.out.println("--------- Test 1: Testing Valid Subdomain, Domain & TLD ---------\n");
		for (int i = 0; i < valid_subdomain_ct; i++) {
			for (int j = 0; j < valid_domain_ct; j++) {
				for (int k = 0; k < valid_tlds_ct; k++) {
					String test_url = "http://" + valid_subdomain[i] + "." + valid_domain[j] + "." + valid_tlds[k]; 
					System.out.println(test_url);
					System.out.println("Expected: true, Actual: " + url_validator.isValid(test_url)); 
		
					if (url_validator.isValid(test_url)) {
						result += 1; 
					}
					compare += 1; 
				}
			}
		}
		
		// TEST VALID LOCALHOST 
		System.out.println("--------- Test 2: Testing Valid LocalHost ---------\n");
		// Test localhost domain, which does not follow format above	
		UrlValidator localhost_val = new UrlValidator(null, null, UrlValidator.ALLOW_LOCAL_URLS);
		String local_url = "http://localhost"; 
		System.out.println(local_url);
		System.out.println("Expected: true, Actual: " + localhost_val.isValid(local_url)); 	// expected false because have not turned on ALLOW_LOCAL_URLS
		if(localhost_val.isValid(local_url) == true) {
			result += 1;
		}
		compare += 1; 
		
		// TEST INVALID URLS
		System.out.println("--------- Test 3: Testing Invalid Subdomain, Domain & TLD ---------\n");
		for (int i = 0; i < invalid_subdomain_ct; i++) {
			for (int j = 0; j < invalid_domain_ct; j++) {
				for (int k = 0; k < invalid_tlds_ct; k++) {
					String test_url = "http://" + invalid_subdomain[i] + "." + invalid_domain[j] + "." + invalid_tlds[k]; 
					System.out.println(test_url);
					System.out.println("Expected: false, Actual: " + url_validator.isValid(test_url)); 
					if (!url_validator.isValid(test_url)) {
						result += 1; 
					}
					compare += 1; 
				}
			}
		}
		
		// TEST INVALID LOCALHOST 
		System.out.println("--------- Test 4: Testing Invalid LocalHost ---------\n");
		// Test invalid localhost domain, which does not follow format above	
		UrlValidator bad_localhost_val = new UrlValidator(null, null, UrlValidator.ALLOW_LOCAL_URLS);
		String bad_local_url = "http://locahost"; 
		System.out.println(bad_local_url);
		System.out.println("Expected: false, Actual: " + bad_localhost_val.isValid(bad_local_url)); 	// expected false because have not turned on ALLOW_LOCAL_URLS
		if(bad_localhost_val.isValid(bad_local_url) == false) {
			result += 1;
		}
		compare += 1; 
		
		// Return 1 if all tests pass, 0 otherwise
		if(compare == result) {
			return 1; 
		}
		else {
			return 0; 
		}

	}
}
