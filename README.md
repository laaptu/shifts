### App Architecture
  * Modular Architecture
  * MVVM with Jetpack ViewModel,LiveData and Coroutines


### Supporting Libraries
* Dagger 2 for Dependency Injection
* Jetpack Android Library
* Retrofit for network communication
* Timber for logging
* Junit5 for Unit testing
* Common helper classes from Plaid App and Github Browser App from Architecture Samples for testing


### Modules

![Main Modules](https://raw.githubusercontent.com/laaptu/uploads/master/pics/shifts_app_architecture.png)


The module division is as follows

* Shared Modules
* Feature Modules
* Main Module

#### Shared Modules
Contains two modules for now 

* Base Module
* Test Module  
 
##### Base Module
 Contains all the shared classes that will be used entirely by all the modules. Currenty this contains base files for dependency injection for Dagger along with helper classes for ViewModels. 

##### Test Module 
Common classes that are used for ViewModel, LiveData and Coroutine testing.
 
#### Feature Modules
  All the features that is required by the app will lie in this feature module. Currently there is only shifts feature and later on more can be added here.
  
##### Shifts Module
This module depends upon the base module and test module from Shared Modules. Uses MVVM architure and mainly contains two functionalities

* List Shift: Displays all the shifts of the user.
* Start/End Shift: Allows the user to add a new shift or end an existing shift
  
#### Main Module
   This is the app module or the main app will use feature modules and base modules as it's dependency.
   