# A simple Android App

## Business Requests
1. show Event List

2. show event details

3. register event (prompt by user login)

4. user login

5. favorite/un-favorite a event

## App Development Design from ZERO

### Design of EventListActivity
I want a page showing the event list, some features need support
- check if the device online or offline (see if wifi or edge network available)
- A loading dialog indicate event list is downloading (it could be cancelled)
- a Retry button when error raised to download event list
- view container of showing event list
- graph of code execution (what's happened)
- page transition when one event select, goto EventDetailsActivity
- a mock event list data source works when in an environment which cannot access remote event list service 


##  Base Architecture

------------------------------------                              
|  View  |  Presenter  |   Error   |
------------------------------------
|             Repository           |
------------------------------------
|             DataSource           |
------------------------------------
|           Infrastructure         |
------------------------------------
| RS |  Sqlite | NoSql-Persistence |
------------------------------------

- View, Activity could be mentioned as a implementation of view
- Presenter, which could be called by view, and callback to view
- Repository, API of required data could be get from, called related DataSource to get data
- DataSource, using RS, Sqlite, or NoSql to perform get data, save data

- App
  |- src
    |- com
      |- yanchuanosaurus
        |- app
          |- contracts
            |- event
              |- EventListContract.java
          |- presenters
            |- EventListPresenter.java
          |- views
            |- event
              |- EventListActivity.java
    |- repository
      |- app
        |- RepositoryManager.java
        |- EventRepository.java
        |- EventRepositoryImpl.java
        |- ds
          |- EventDataSource.java

## Code Generation

# Components Solution
CTMediator
  openUrl, params
  parseUrl, parse params
  perform, target.action(params) of openUrl
Runtime
  create instance of target
  perform action with params

Question:
- 1. How to manage the instances of target?
- 2. What's the component? Only a concept intheory？
  - 2.1 Everything is component?
  - 2.2 Everything could has action?
  - 2.3 I agree about no model in component

# Model Solution
What should we want?
- Create App project. (No features here)
- Create a new feature
  - create couple of views
  - create models related (*how to modeless?*)
  - create classes related get/save models
  - connect classes with views and models
  - add the feature to the app (how to add to app?)
- Create another feature
  - create couple of views
  - create models related (*how to modeless?*), but if the model related to other features, we should upgrade the model cross features
  - create classes related get/save models, but if the classes related to other features?
  - connect classes with views and models
  - add the feature to the app (how to add to app?)