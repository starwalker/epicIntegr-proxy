# proxy



## Getting started

To make it easy for you to get started with GitLab, here's a list of recommended next steps.

Already a pro? Just edit this README.md and make it your own. Want to make it easy? [Use the template at the bottom](#editing-this-readme)!

## Add your files

- [ ] [Create](https://docs.gitlab.com/ee/user/project/repository/web_editor.html#create-a-file) or [upload](https://docs.gitlab.com/ee/user/project/repository/web_editor.html#upload-a-file) files
- [ ] [Add files using the command line](https://docs.gitlab.com/ee/gitlab-basics/add-file.html#add-a-file-using-the-command-line) or push an existing Git repository with the following command:

```
cd existing_repo
git remote add origin https://git.musc.edu/epicdev/rsch-pipeline/gateway-cdspipeline/proxy.git
git branch -M main
git push -uf origin main
```

## Integrate with your tools

- [ ] [Set up project integrations](https://git.musc.edu/epicdev/rsch-pipeline/gateway-cdspipeline/proxy/-/settings/integrations)

## Collaborate with your team

- [ ] [Invite team members and collaborators](https://docs.gitlab.com/ee/user/project/members/)
- [ ] [Create a new merge request](https://docs.gitlab.com/ee/user/project/merge_requests/creating_merge_requests.html)
- [ ] [Automatically close issues from merge requests](https://docs.gitlab.com/ee/user/project/issues/managing_issues.html#closing-issues-automatically)
- [ ] [Enable merge request approvals](https://docs.gitlab.com/ee/user/project/merge_requests/approvals/)
- [ ] [Set auto-merge](https://docs.gitlab.com/ee/user/project/merge_requests/merge_when_pipeline_succeeds.html)

## Test and Deploy

Use the built-in continuous integration in GitLab.

- [ ] [Get started with GitLab CI/CD](https://docs.gitlab.com/ee/ci/quick_start/index.html)
- [ ] [Analyze your code for known vulnerabilities with Static Application Security Testing (SAST)](https://docs.gitlab.com/ee/user/application_security/sast/)
- [ ] [Deploy to Kubernetes, Amazon EC2, or Amazon ECS using Auto Deploy](https://docs.gitlab.com/ee/topics/autodevops/requirements.html)
- [ ] [Use pull-based deployments for improved Kubernetes management](https://docs.gitlab.com/ee/user/clusters/agent/)
- [ ] [Set up protected environments](https://docs.gitlab.com/ee/ci/environments/protected_environments.html)

***

## Project status

# FHIR API-Based Epic EMR/EHR Gateway Application Documentation 

  

## Overview 

  

This document outlines the development and deployment of a FHIR API-based gateway application for integrating with Epic and 3rd party application systems. The application leverages concepts and components from existing open-source projects, namely HAPI,  Microsoft's FHIR Proxy, Kubernetes, etc... The gateway will facilitate secure and efficient data exchange between Epic systems and external applications, adhering to HL7 FHIR standards. 

 

## Components and Architecture 

  

### 1. BMIC Gateway (pipelines) 

- **Repository:** [BMIC Gateway](https://git.musc.edu/epicdev/rsch-pipeline/gateway-cdspipeline) 

- **Purpose:** Provides a secure, scalable interface to access FHIR/Clarity/Caboodle/REDCap/Oncore/Snowflake/Auzre/SQL server  resources using microservices. 

- **Features:** 

  - OAuth2 authentication and authorization. 

  - Data transformation and validation. 

  - Integration with Azure Healthcare API FHIR, DICOM, and MedTech services. 

- **Tech Stack:** 

  - **Programming Language:** Java/Scala/Python/Typescript/SQL 

  - **Frameworks:** Spring Boot/Quarku/Angular/K8s 

  - **Cloud Services:** Azure, Azure Healthcare Services 

  - **Authentication:** OAuth2, OpenID Connect 

  - **Database:** SQL Server, Azure SQL database, and Redis 

 

  - **Data security and compliance policies :** Cloudquery for cloud based app  DBT for non-cloud based app. 

 

- **High-level Architecture:** structured around several core components: 

 

API (Proxy) Gateway: Serves as the entry point for all incoming requests, routing them to the appropriate backend services while handling load balancing, authentication, and rate limiting.  

Service Registry (Event) : Dynamically manages service instances and their locations for effective routing and load balancing. 

API Management (K8s): Provides tools for API creation, publishing, and analytics. 

Security Service (built in, cloudquery): Handles all aspects of security including SSL termination, OAuth 2.0, and JWT for authentication and authorization. 

Data Transformation Service(built in, dbt): Transforms data between different formats (e.g., JSON, XML) as needed by different consumer applications. 

Logging and Monitoring: Monitors services and logs events and metrics for performance and troubleshooting. 

- **Development Flow:** structured around several core components: 

The specification might be changed during the process, and the changes will be propagated to the API implementation team and Client implementation team(s).	 

### 2. Event Gateway (Event driven) 

- **Repository:** [ Event Gateway ](https://git.musc.edu/epicdev/rsch-pipeline/gateway-cdspipeline/eventcenter) 

- **Purpose:** Facilitates integration with different FHIR servers, providing middleware for data access and transformation between 3rd party and MUSC internal resources, event driven gateway, the original purpose of this one is rapid randomized screening, data extraction, de-duplication and randomization arm in a medical setting. 

- **Features:** 

  - Support for multiple FHIR versions, tested in Epic and Cener test sandbox. 

  - Comprehensive logging and monitoring for the whole research study/clinical trial lifecycle. 

  - Middleware capabilities for data routing and transformation. 

- **Tech Stack:** 

  - **Programming Language:** Java, Go, Scala, TypeScript, JavaScript, SQL 

  - **Frameworks:** Node.js, Express, Auglar, Quarkus, K8s, Camel 

  - **Database:** MongoDB, PostgreSQL, SQL Server, Azure SQL Database, Redis 

  - **Containerization:** Docker 

  - **Orchestration:** Kubernetes 

  - **Logging:** ELK Stack (Elasticsearch, Logstash, Kibana, REDcap(Perfermance issue)) 

  

### 3. Proxy Gateway (Data driven) 

- **Repository:** [Proxy](https://git.musc.edu/epicdev/rsch-pipeline/gateway-cdspipeline/proxy.git) 

- **Purpose:** Acts as a security and transformation layer for FHIR API requests, enhancing security and data governance. 

- **Features:** Auto deployment, Compliance with data privacy regulations: Implementing data privacy measures in DBT helps organizations comply with regulations like GDPR, HIPAA, and CCPA that govern the responsible handling and protection of personal information 

  - Role-based access control (RBAC). 

  - Data transformation and filtering. 

  - Integration with Azure API Management for enhanced security. 

- **Tech Stack:** 

  - **Programming Language:** Java, Scala, Python, SQL 

  - **Frameworks:** .K8s, Spring Boot, Quarkus, Camel,  

  - **Cloud Services:** Microsoft Azure, Azure API Management 

  - **Authentication:** OAuth2, OpenID Connect 

  - **Database:** Azure Cosmos/SQL DB, SQL Server, Redis 

  

## Application Architecture 

  

The bmic, event and proxy gateway application for Epic will integrate the strengths of these projects into a cohesive architecture: 

  

1. **Authentication and Authorization:** 

   - Implement OAuth2 and OpenID Connect based on Proxy Gateway for secure access control. 

   - Utilize Azure Active Directory (AAD) for identity management as supported by Microsoft FHIR Proxy if the whole thing is deployed to Azure. 

  

2. **Data Transformation and Validation:** 

   - Incorporate Azure/ data transformation and validation tools to ensure FHIR resources are consistent with Epic's requirements.  

   - Use IBM/VA based middleware capabilities to handle different FHIR versions and transform data as needed. 

    - Use opensource DBT core capabilities to handle different FHIR versions and transform non-FHIR related data as needed. 

Compliance with data privacy regulations: Implementing data privacy measures in dbt helps organizations comply with regulations like GDPR, HIPAA, and CCPA that govern the responsible handling and protection of personal information. 

Preventing unauthorized access and data misuse: Data privacy controls in dbt, such as access policies and anonymization, help prevent unauthorized access and misuse of sensitive data, reducing the risk of data breaches and identity theft. 

Building trust and reputation: By prioritizing data privacy, organizations can build trust with customers and stakeholders, and maintain a positive brand reputation. 

Enabling secure data analytics: dbt's data privacy features, like row-level access policies and data anonymization, allow organizations to perform analytics on sensitive data while minimizing privacy risks. 

Cost savings: Automating data privacy controls through dbt can lead to cost savings by reducing the manual effort required for data minimization and compliance. 

Empowering data contributors: Granular access controls in dbt enable organizations to empower more users to work with data, while ensuring they only see what they need to see.  

 

 

3. **Logging and Monitoring:** 

   - Deploy comprehensive logging and monitoring solutions using MIRACUM's logging framework. 

   - Integrate with email and redcap alert for real-time insights and alerts. 

4. **Security and Compliance:** 

   - Enforce RBAC and data filtering as provided by Microsoft FHIR Proxy to ensure data privacy and compliance with regulations such as HIPAA and GDPR. 

   - Implement audit logging to track access and modifications to FHIR resources. 

  

## Deployment 

 Kubernetes deployment with replica sets 

 

### Prerequisites 

- An instance of tech stack and application architecture support. 

- Docker and Kubernetes for containerized deployment. 

 

### Steps 

  

1. **Set Up OAuth2 Authentication:** 

   - Configure OAuth2 endpoints in Azure AD / K8S cluster. 

   - Set up client applications in Epic and external systems to use OAuth2 for authentication. 

  

2. **Deploy FHIR Gateway Components:** 

   - Clone the repositories from git.musc.edu. 

   - Configure and deploy BMIC Gateway on Auzre or a Kubeernetes cluster. 

   - Set up Microsoft FHIR Proxy on Azure, ensuring integration with Azure API Management if the deployment environment is Azure. 

 

3. **Configure Data Transformation and Middleware:** 

   - Use Gateway's tools to configure data validation and transformation rules. 

   - Set up middleware logic in Gateway to handle different FHIR versions, data resources and data routing. 

  

4. **Integrate Logging and Monitoring:** 

   - Deploy gateway lifecycle logging framework and configure it to send logs to a centralized logging service (redcap or sql server). 

   - Integrate with Azure Monitor Monitoring for comprehensive observability if the deployment environment is Azure. 

  

5. **Test and Validate:** 

   - Perform end-to-end testing to ensure secure and correct data exchange between Epic and external applications. 

   - Validate that all FHIR resources are transformed and validated correctly. 

  

### Example Configuration 

  

#### OAuth2 Configuration (Local k8s FHIR Gateway) 

```yaml 

oauth2: (Create your own bmic gateway client) 

  client_id: YOUR_CLIENT_ID 

  client_secret: YOUR_CLIENT_SECRET 

  token_uri: https://oauth2.musc.edu/token 

  auth_uri: https://oauth2.musc.edu/o/oauth2/auth 

  redirect_uris: 

    - https://your-application/callback 

``` 

  

#### Kubernetes Deployment (proxy and event Gateway) 

```yaml 

apiVersion: apps/v1 

kind: Deployment 

metadata: 

  name: your client’s name 

spec: 

  replicas: 3 

  template: 

    metadata: 

      labels: 

        app: your app’s name 

    spec: 

      containers: 

        - name: proxy-gateway 

          image: musc/proxy-gateway:latest 

          ports: 

            - containerPort: 8080 

          env: 

            - name: can be config in gateway config settings 

              value: "https://your-fhir-server" 

            - name: LOG_LEVEL 

              value: "INFO" 

``` 

  

#### RBAC Configuration (Microsoft FHIR Proxy – For Azure) 

```json 

{ 

  "policies": [ 

    { 

      "name": "AdminAccess", 

      "actions": ["read", "write", "delete"], 

      "resources": ["Patient", "Observation", "Encounter"] 

    }, 

    { 

      "name": "ReadOnlyAccess", 

      "actions": ["read"], 

      "resources": ["Patient", "Observation"] 

    } 

  ], 

  "roles": [ 

    { 

      "name": "Admin", 

      "policies": ["AdminAccess"] 

    }, 

    { 

      "name": "Viewer", 

      "policies": ["ReadOnlyAccess"] 

    } 

  ], 

  "users": [ 

    { 

      "username": "admin_user", 

      "roles": ["Admin"] 

    }, 

    { 

      "username": "viewer_user", 

      "roles": ["Viewer"] 

    } 

  ] 

} 

``` 

  

## Conclusion 

  

By leveraging the features and strengths of Google FHIR Gateway, MIRACUM FHIR Gateway, and Microsoft FHIR Proxy, this FHIR API-based gateway application provides a robust, secure, and scalable solution for integrating Epic EMR/EHR systems with external applications. The modular architecture ensures flexibility and ease of deployment, making it adaptable to various healthcare environments and requirements. 
