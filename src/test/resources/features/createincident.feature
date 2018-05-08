Feature: Being able to create an incident
Scenario: Create incident
    Given an Agents:
      | id    	| password 	| kind 	|
      | 5		| pepe123	|	1	|
    
    When I login as an agent with name "5", password "pepe123" and kind "1"
    And I can create a new incident with incidentName ,"terremoto" , description "earthquake" , locationString "4.54,6.78" , tags "earthquake" , aditionalPropertiesString "casas/derruidas" , topic "earthquake"
	Then I can verify it	
 