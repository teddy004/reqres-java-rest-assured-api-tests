Feature: ReqRes Users API
  Validate ReqRes API for user operations including GET, POST, PATCH, and DELETE

  Scenario: Get users list (default)
    When I request the users list
    Then the response status should be 200
    And the Content-Type header should contain JSON
    And the response should contain a non-empty data array

  Scenario Outline: Get users list with pagination
    When I request the users list with page <page> and per_page <per_page>
    Then the response status should be 200
    And the Content-Type header should contain JSON
    And the response should contain a non-empty data array
    And the page should be <page>
    And the per_page should be <per_page>

    Examples:
      | page | per_page |
      | 1    | 3        |
      | 2    | 2        |
      | 1    | 6        |

  Scenario: Get single user by id
    When I request user with id "2"
    Then the response status should be 200
    And the response should contain data.id equal to "2"

  Scenario: Get single user not found
    When I request user with id "23"
    Then the response status should be 404

  Scenario: Update user id 2 with payload
    Given I have a user payload with name "morpheus" and job "zion resident"
    When I update user id "2"
    Then the response status should be 200
    And the response should contain job equal to "zion resident"
    And the response should contain updatedAt
    When I request user with id "2"
    Then the response should contain data.id equal to "2"

  Scenario: User CRUD lifecycle (create -> update -> delete)
    Given I have a user payload with name "morpheus" and job "leader"
    When I create the user
    Then the response status should be 201
    And the response should contain a non-empty id
    And the response should contain name "morpheus"
    And the response should contain job "leader"
    And the response should contain createdAt

    Given I have a user payload with name "neo" and job "the one"
    When I update the created user
    Then the response status should be 200
    And the response should contain job equal to "the one"
    And the response should contain updatedAt

    When I delete the created user
    Then the response status should be 204

  Scenario: Get resources list
    When I request the resources list
    Then the response status should be 200
    And the response should contain a non-empty data array

  Scenario: Get single resource by id
    When I request resource with id "2"
    Then the response status should be 200
    And the response should contain data.id equal to "2"

  Scenario: Get single resource not found
    When I request resource with id "23"
    Then the response status should be 404

  Scenario: Register - successful
    Given I have an auth payload with email "eve.holt@reqres.in" and password "pistol"
    When I register
    Then the response status should be 200
    And the response should contain token

  Scenario: Register - unsuccessful
    Given I have an auth payload with email "sydney@fife"
    When I register
    Then the response status should be 400
    And the response should contain error "Missing password"

  Scenario: Login - successful
    Given I have an auth payload with email "eve.holt@reqres.in" and password "cityslicka"
    When I login
    Then the response status should be 200
    And the response should contain token

  Scenario: Login - unsuccessful
    Given I have an auth payload with email "peter@klaven"
    When I login
    Then the response status should be 400
    And the response should contain error "Missing password"

  Scenario: Delayed response
    When I request users with delay 3
    Then the response status should be 200
    And the response should contain a non-empty data array