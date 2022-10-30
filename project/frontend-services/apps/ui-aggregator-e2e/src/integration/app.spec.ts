describe('ui-aggregator', () => {
  beforeEach(() => cy.visit('/'));

  it('should display welcome message for anonymous user', () => {
    cy.anonymous();
    cy.contains("Valid Credentials");
    cy.logout();
  });
});
