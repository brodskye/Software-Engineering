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
 * @version $Revision: 1128446 $ $Date: 2011-05-27 13:29:27 -0700 (Fri, 27 May 2011) $
 */
public class UrlValidatorTest extends TestCase {

   private boolean printStatus = false;
   private boolean printIndex = false;//print index that indicates current scheme,host,port,path, query test were using.

   public UrlValidatorTest(String testName) {
      super(testName);
   }
   
   public static void testManualTest()
   {
	   UrlValidator urlVal = new UrlValidator(null, null, UrlValidator.ALLOW_ALL_SCHEMES);
	   //System.out.println(urlVal.isValid("http://www.amazon.com"));
	   
	   System.out.println("------------------------- MANUALLY TESTING URLS -----------------------\n");
	   
	   System.out.println("TEST 1: PROTOCOLS");
	   
	   //manual test protocol
	   System.out.println("URL: \"https://www.amazon.com\"");
	   System.out.println("Expected: True, Actual: " + urlVal.isValid("https://www.amazon.com"));	//expected = pass, actual = pass
	   System.out.println("\nURL: \"ftp://www.amazon.com\"");
	   System.out.println("Expected: True, Actual: " + urlVal.isValid("ftp://www.amazon.com"));		//expected = pass, actual = pass
	   System.out.println("\nURL: \":www.amazon.com\"");
	   System.out.println("Expected: True, Actual: " + urlVal.isValid(":www.amazon.com"));			//expected = fail, actual = pass -- bug
	   System.out.println("\nURL: \"100e://www.amazon.com\"");
	   System.out.println("Expected: True, Actual: " + urlVal.isValid("100e://www.amazon.com"));	//expected = fail, actual = fail
	   System.out.println("\nURL: \"asdf://www.amazon.com\"");
	   System.out.println("Expected: True, Actual: " + urlVal.isValid("asdf://www.amazon.com"));	//expected = pass, actual = pass
	   System.out.println("\nURL: \"www.amazon.com\"");
	   System.out.println("Expected: True, Actual: " + urlVal.isValid("www.amazon.com"));			//expected = fail, actual = fail

	   System.out.println("\nURL: \"http://wwk.amazon.com\"");
	   System.out.println("Expected: False, Actual: " + urlVal.isValid("http://wwk.amazon.com/"));

	   System.out.println("\nURL: \"http://8ww.amazon.com\"");
	   System.out.println("Expected: False, Actual: " + urlVal.isValid("http://8ww.amazon.com"));

	   System.out.println("\nURL: \"http://w&w.amazon.com\"");
	   System.out.println("Expected: False, Actual: " + urlVal.isValid("http://w&w.amazon.com"));

	   System.out.println("\nURL: \"http://.amazon.com\"");
	   System.out.println("Expected: False, Actual: " + urlVal.isValid("http://.amazon.com"));

	   System.out.println("\nURL: \"\"");
	   System.out.println("Expected: False, Actual: " + urlVal.isValid(""));

	   System.out.println("\nURL: \"http://www.asdf&8.com\"");
	   System.out.println("Expected: False, Actual: " + urlVal.isValid("http://www.asdf&8.com"));

	   System.out.println("\nURL: \"http://256.295.153.25\"");
	   System.out.println("Expected: False, Actual: " + urlVal.isValid("http://256.295.153.25"));

	   System.out.println("\nURL: \"http://www..com\"");
	   System.out.println("Expected: False, Actual: " + "http://www..com"));

	   System.out.println("\nURL: \"http://www.asdf\"");
	   System.out.println("Expected: False, Actual: " + urlVal.isValid("http://www.asdf"));
	   
	   System.out.println("\nURL: \"http://www.amazon.com5\"");
	   System.out.println("Expected: False, Actual: " + urlVal.isValid("http://www.amazon.com5"));

	   System.out.println("\nURL: \"http://www.amazon.i\"");
	   System.out.println("Expected: False, Actual: " + urlVal.isValid("http://www.amazon.i"));

	   System.out.println("\nURL: \"http://www.amazon.i\"");
	   System.out.println("Expected: False, Actual: " + urlVal.isValid("http://www.amazon.555"));

	   System.out.println("\nURL: \"https://www.amazon.com/b?&node=17285120011&=value\"");
       System.out.println("Expected: True, Actual: " + urlVal.isValid("https://www.amazon.com/b?&node=17285120011&=value"));

	   System.out.println("\nURL: \"https://www.amazon.com/156\"");
       System.out.println("Expected: True, Actual: " + urlVal.isValid("https://www.amazon.com/156"));

	   System.out.println("\nURL: \"https://www.google.com/1%5E&\"");
       System.out.println("Expected: True, Actual: " + urlVal.isValid("https://www.google.com/1%5E&"));

	   System.out.println("\nURL: \"http://www.amazon.com/]\"");
       System.out.println("Expected: False, Actual: " + urlVal.isValid("http://www.amazon.com/]"));

	   System.out.println("\nURL: \"http://www.amazon.com:80\"");
       System.out.println("Expected: True, Actual: " + urlVal.isValid("http://www.amazon.com:80"));

	   System.out.println("\nURL: \"http://www.amazon.com:k\"");
	   System.out.println("Expected: False, Actual: " + urlVal.isValid("http://www.amazon.com:k"));   
   }
   
   // main test function to test all input partitions
   public static void testAllPartitions() {
	   
	   System.out.println("\n------------------------- INPUT PARTITIONING TESTS -----------------------\n");
	   
	   UrlValidator urlVal = new UrlValidator(null, null, UrlValidator.ALLOW_ALL_SCHEMES);
	   
	   //input partition table scheme
	   boolean[][] table_scheme = {
	        {true,true,true,true},
	        {false,false,false,false},
	        {false,true,true,true},
	        {true,false,true,true},
	        {true,true,false,true},
	        {true,true,true,false},
	        {false,false,true,true},
	        {false,true,false,true},
	        {true,false,true,false},
	        {true,true,false,false},
	        {false,true,true,false},
	        {true,false,false,true},
	        {false,false,false,true},
	        {true,false,false,false},
	        {false,false,true,false},
	        {false,true,false,false}
	    };

	    //loop through boolean 2D array
	    for(int i = 0; i < table_scheme.length; i++){
	        	
			//loop through partitions
	        for(int j = 0; j < 5; j++){
	            String test_url = testProtocolInput(table_scheme[i][0], j) + testAuthorityInput(table_scheme[i][1], j) 
	                + testPortInput(table_scheme[i][2], j) + testPathInput(table_scheme[i][3], j);
	            
	            //get expected boolean for this URL
	            boolean check_expected = table_scheme[i][0] && table_scheme[i][1] && table_scheme[i][2] && table_scheme[i][3];
	            
	            //print out URL and expected/actual results
	            System.out.println(test_url);
	            System.out.println("Expected: " + check_expected + ", Actual: " + urlVal.isValid(test_url) + "\n");
	        }

	    }
   }
   
	 //input: 
	   //boolean: returns a string from either a valid string array or invalid string array
	   //integer: index value of array
	//precondition: all partition array sizes must be the same length
	public static String testProtocolInput(boolean isValid, int index){
		
	   //array for valid protocols
	   String[] valid = {"http://", "https://", "ftp://", "cid://", "mailto:", ""};
	   
	   //array for invalid protocols
	   String[] invalid = {"345://", "http:/", "https:", "https/", ":::", "-&@"};
	
	   if(isValid){
	       return valid[index];
	   }else{
	       return invalid[index];
	   }
	
	}
   
//Partition function to test invalid and vaild authoritys with the URLValidator() function provided by Apache Commons. 
   public static string testAuthorityInput(boolean isValid, int index)
   {
	   //Valid authority String array of size 6
	   String[] valid = {"www.facebook.com", "amazon.com", "255.255.255.255", "facebook.cc", "facebook.au", "1.1.1.1"}; 
	   
	   //Invalid authority String array of size 6
	   String[] invalid = {"www.website.invalid", "amazon.123", "300.300.300.300", "1.1.1.1.1", "abcdefghijklmnop", ""};

	   //Returning string at current index of the valid or invalid authority to main function.
	   if(isValid)
	  	{
			return valid[index];
		}
	   else
   		{
			return invalid[index];
   		}
   }
   
   	public static String testPortInput(boolean isValid, int index){
	
	   // Arrays of valid and invalid ports
	   // Reference: https://stackoverflow.com/questions/113224/what-is-the-largest-tcp-ip-network-port-number-allowable-for-ipv4
	   String[] valid = {":8080", ":65535", ":1"};
	   String[] invalid = {":", ":65536", ":x"}; 

	   // Return valid or invalid port string at location within array specified, depending on arguments
	   if(isValid){
	       return valid[index];
	
	   }else{
	       return invalid[index];
	   }
	}

	public static String testPathInput(boolean isValid, int index){
		// Reference: https://stackoverflow.com/questions/4669692/valid-characters-for-directory-part-of-a-url-for-short-links
		// Arrays for valid and invalid paths/queries
		String[] valid = {"/", "", "/a" };
		String[] invalid = {"?", "///", "#"}; 

		
	    // Return valid or invalid path string at location within array specified, depending on arguments
		if(isValid){
		    return valid[index];
		}else{
		    return invalid[index];
		}
	}

   public void testYourSecondPartition(){
	   
   }
   
   
   public void testIsValid()
   {
	   for(int i = 0;i<10000;i++)
	   {
		   
	   }
   }
   
   public void testAnyOtherUnitTest()
   {
	   
   }
   /**
    * Create set of tests by taking the testUrlXXX arrays and
    * running through all possible permutations of their combinations.
    *
    * @param testObjects Used to create a url.
    */
   

}
