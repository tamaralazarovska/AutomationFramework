@tag
Feature: Adding product to wishlist
  I want to use this template for my feature file

	Background:
	Given I landed on Bugaboo page

  @Regression
  Scenario Outline: Positive Test of adding product to wishlist

    Given Logged in with username <name> and password <password>
    When I add product <productName> to Cart
    Then "The product has been added to your wishlist." message is displayed

    Examples:
      | name  								|  password		    |	productName |
      | tamara.lazarovska12@valtech.com |  Daniela76    | Dragonfly |