Feature: Being able to login
Scenario: Login
  Login with some username

    Given a list of agents:
      | id    	| password 	| kind 	|
      | 5		| pepe123	|	1	|
      | 333 	 	| juan123	|	1	|
    
    Then I login with name "5", password "pepe123" and kind "1"
    Then I can create a new incident
 
 