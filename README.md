# RobbieAndroidUtil

I built this project to be used as a helper for my own projects, usually my projects of test

Each module has a few helpers and utils that give me a few useful tools such as modals, whitelabel tools, base class for ViewModel, Activity and so on.

## Modules

### androidtools (Android Tools)
Contains utils for date, file, log (system print) and crash.

### app
Used to test a few functions and views.

### baselifecycle
Contains base class for Activity, Fragment and View Model, also a keyboard helper and observers based on live data.

### buildSrc
Responsible for the project dependencies.

### buttons
Based on whitelabel, it has the main styles of buttons, they are Primary, Secondary, Transparent and Under.
> Using only the styles and replacing the tokens I can make configurable and reusable buttons for the whole app.

### core
Based on whitelabel, it has all main token used for the whitelabel components.

### sqlitetools
A helper for export and mock tables based on SQLite. It can get the main info about a table and can insert mocked data for test using mainly random values

### sqlitetools
Also base on whitelabel, it has the styles of edit texts, they are Filled, Outline and Under.
> Using only the styles and replacing the tokens I can make configurable and reusable edit texts for the whole app.

### typography
Also base on whitelabel, it has the styles of text views, they are Normal, Normal Bold, Brand and Brand Bold, also contains styles based on H (1 to 6), normal, bold and brand, also Paragraph (1 to 3), normal, bold and brand, and also Subtitle (1 to 3), normal, bold and brand.
> Using only the styles and replacing the tokens I can make configurable and reusable text views for the whole app.

### widgets
Hold a few utils for views and contains a generic Dialog with easy usuability.
</br>
And progress controll that adds a progress to the app, also generic, and can controlls who/when shows and hide the progress, usefull for multitasking that demands to show the progress until all tasks are done.
