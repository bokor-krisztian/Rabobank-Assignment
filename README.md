# Rabobank-Assignment

![alt text](https://raw.githubusercontent.com/krisztian-bokor/Disclosure/master/docs/architecture.png?token=APOVEBGS6UUHZ32JB3TUBFTABA5VW)

## Modules (Layers)
### App
  - DI : Koin
### Presentation
  - consists of Views and ViewModels
  - 2 types of communication ViewModel -> View : UiState & UiEvent
  - UiState consists of the state of the View persisted in the ViewModel
  - UiEvent consists of a one time action exposed by the ViewModel to the View
  - expose LiveData\<UiState> to ensure the correct state is persisted in ViewModel
  - expose SingleLiveData\<UiEvent> to ensure that the event is triggered only once
### Domain
  - contains the business logic encapsulated in interactors (UseCases)
  - each UseCase returns an UseCaseResult to easily process the result in the ViewModel
### Data
  - repositories which can retrieve the data from remote or local sources
