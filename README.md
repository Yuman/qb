Quickbooks Adapter
==================

The Quickbooks Adapter enables internet access to Quickbooks data, which is usually protected by firewalls. this can be used as a replacement to Intuit Quickbooks Web Connector that runs on on XML/SOAP,  while this runs on a microservices architecture, communicating in REST/JSON. It actively checks a broker for database queries, runs them on an QODBC connection, and returns the result, then deletes the queries on the broker.

Design Principles
-----------------

### Command-query Separation
This simplifies the server API implementatio. The API therefore follows the DOT priciple (do one thing at a time), with no concern with workflow.

### Idempotency of Operations
This enhances the reliability of the client and the server by leaving no side-effects, and allowing retries on failure.
