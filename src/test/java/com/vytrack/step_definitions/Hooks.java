package com.vytrack.step_definitions;

import com.vytrack.utils.Driver;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.util.concurrent.TimeUnit;

/*
    -Hooks name is not resevred. You may name this class in any way.
    -For example: SuiteSetupAndTearDown
    -Hooks triggered based on tags not class name or their location.
    -This methods can be a part of any step definition class.
    -Common practice is to store them inthe separate class.
*/

public class Hooks {

//  hook before = @BeforeMethod in TestNG
//  hook after = @AfterMethod in TestNG
//  It's not a good idea to mix implicit & explicit waits.It can lead to unexpectedly long waits.
//  Usually, we just use explicit and fluent waits.

    @Before
    public void setup(Scenario scenario){
        System.out.println("::: Starting Automation :::");
        Driver.getDriver().manage().window().maximize();
        Driver.getDriver().manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
    }

    // This hook will run only before scenarios with tag @db
/*
    @db
    Scenario: I don't know but here i'm connecting to DB
    Given user runs following query "SELECT *..."

    order = 0 - to define hooks execution order
*/
    @Before("@db")
    public void dbSetup(){
        System.out.println("::: Connecting to database :::");
    }

    @After("@db")
    public void dbTearDown(){
        System.out.println("::: Disconnecting from the database :::");
    }

    @After
    public void tearDown(Scenario scenario){
        //this is a hook after
        //runs automatically after every test
        if (scenario.isFailed()){
            byte[] data = ((TakesScreenshot)Driver.getDriver()).getScreenshotAs(OutputType.BYTES);
            scenario.attach(data, "image/png", scenario.getName());
        }



        Driver.closeDriver();
        System.out.println(":<(^_^)>: End of task execution :<(^_^)>:");
    }

}
