# Nutrition Tracker app for final project in Android development semester 6

when in progress of something remove :x: <br>
when finished with task add :heavy_check_mark:

| Contributor | Index |
| ----------- | ----------- |
| Jelena Mijatović | 114/21 RN |
| Petar Stamenić | 77/21 RN |


## TODO
### 1 Splash Activity
> :heavy_check_mark: 1.1 add splash activiy 
### 2 Login Activity
> :heavy_check_mark: 2.1 add login activity <br>
> :heavy_check_mark: 2.2 add saving user to sharded preference or database <br>
> :heavy_check_mark: 2.3 add rules for username and password <br>
### 3 Main Activity
> #### 3.1 Choose category fragment
>> 3.1.1 :heavy_check_mark: add data class for categories <br>
>> 3.1.2 :heavy_check_mark: add api call to retrive categories <br>
>> 3.1.3 :heavy_check_mark: add layout for choose category fragment <br>
>> 3.1.4 :heavy_check_mark: add saving to repository chosen category <br>
>> 3.1.5 :heavy_check_mark: add category adapter <br>
>> 3.1.5 :heavy_check_mark: add functionality to fragment <br>
> #### 3.2 Meal List Fragment
>> 3.2.1 :heavy_check_mark: add data class for short meal response and full meal response
>> ##### 3.2.2 Add api calls
>>> 3.2.2.1 :heavy_check_mark: add api calls for short meals by category <br>
>>> 3.2.2.2 :heavy_check_mark: add api calls for short meals by area <br>
>>> 3.2.2.3 :heavy_check_mark: add api calls for short meals by ingredient <br>
>>> 3.2.2.4 :heavy_check_mark: add api calls for detailed meal by name part <br>
>>> 3.2.2.5 :heavy_check_mark: add api calls for detailed meal by first letter <br>
>>> 3.2.2.6 :heavy_check_mark: add api calls for getting all the areas <br>
>>> 3.2.2.7 :heavy_check_mark: add api calls for getting all the ingredients <br>
>>> 3.2.2.8 :heavy_check_mark: add api calls for calories
>> ##### 3.2.3 Add filtering options
>>> 3.2.3.1 :heavy_check_mark: add filtering options layout <br>
>>> 3.2.3.2 :heavy_check_mark: add code implementation for filtering by category <br>
>>> 3.2.3.3 :heavy_check_mark: add code implementation for filtering by area <br>
>>> 3.2.3.4 :heavy_check_mark: add code implementation for filtering by ingredient <br>
>>> 3.2.3.5 :heavy_check_mark: add code implementation for filtering by one of the previous conditions and calories <br>
>>> 3.2.3.6 :heavy_check_mark: add all fileds to the repository for area ingredient search and calories <br>
>> ##### 3.2.4 Add listing of meals
>>> 3.2.4.1 :heavy_check_mark: add layout for listin all the meals <br>
>>> 3.2.4.2 :heavy_check_mark: add a fetch images from the internet <br>
>>> 3.2.4.3 :heavy_check_mark: add meal adapter <br>
>>> 3.2.4.4 :heavy_check_mark: add onClickListener to go to new fragment <br>
>> ##### 3.2.5 Calories
>>> 3.2.5.1 :heavy_check_mark: add estimation of the mesurements for unknown mesures <br>
>>> 3.2.5.2 :heavy_check_mark: add converter for mesurements <br>
>>> 3.2.5.3 :heavy_check_mark: add notification for unknown ingredients <br>
>>> 3.2.5.4 :heavy_check_mark: add caluclation for total number of calories per recepie
>> ##### 3.2.6 New Meal
>>> 3.2.6.1 :heavy_check_mark: add saving meals to database <br>
>>> 3.2.6.2 :heavy_check_mark: add layout for adding new meal <br>
>>> 3.2.6.3 :heavy_check_mark: adding new meal functionality <br>
> #### 3.3 Meal Single Fragment
>> 3.3.1 :heavy_check_mark: add layout for detailed view of one meal <br>
>> 3.3.2 :heavy_check_mark: add call for Random Meal of the day if no meal was selected <br>
>> 3.3.3 :heavy_check_mark: add Random meal of the day if no meal was selected <br>
>> 3.3.4 :heavy_check_mark: add call for meal by id <br>
>> 3.3.5 :heavy_check_mark: add a fetch for image from the internet <br>
>> 3.3.6 :heavy_check_mark: add better looking text formating <br>
>> 3.3.7 :heavy_check_mark: add calories calculation for the meal <br>
>> 3.3.8 :heavy_check_mark: add option to take a new photo <br>
>> 3.3.9 :heavy_check_mark: add option to add meal to the plan <br>
> #### 3.4 Daily plan fragment
>> 3.4.1 :heavy_check_mark: add layout for daily plan <br>
>> 3.4.2 :heavy_check_mark: add listing for all the meals in the day <br>
>> 3.4.3 :heavy_check_mark: add total calories for the plan <br>
>> 3.4.4 :heavy_check_mark: add formating for the plan to text <br>
>> 3.4.5 :heavy_check_mark: add emailing option for the plan <br>
>> 3.4.6 :heavy_check_mark: add option to switch to history <br>
> #### 3.5 History fragment
>> 3.5.1 :heavy_check_mark: add layout for history <br>
>> 3.5.2 :heavy_check_mark: add option to switch to daily plan <br>
>> 3.5.3 :heavy_check_mark: add option to check past meals <br>
>> 3.5.4 :heavy_check_mark: add option to check favorite meals <br>
>> 3.5.5 :heavy_check_mark: add option to filter history by all the filter categories above <br>
> #### 3.6 Statistics fragment
>> 3.6.1 :heavy_check_mark: add layout for statistics <br>
>> 3.6.2 :heavy_check_mark: add graphing for statistics <br>
>> 3.6.3 :heavy_check_mark: load data from database <br>
