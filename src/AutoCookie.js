// ==UserScript==
// @name       CookieClicker Bot
// @version    3
// @description  Attempts to plays coockie clicker in the most efficient way without clicking on golden/red cookies or the cookie
// @match    http://orteil.dashnet.org/cookieclicker/
// @grant none
// @copyright  2015, Samuel Martins
// ==/UserScript==
/* Test Saves
 bingo sooner
 MS4wNDUzfHwxMzk4NTc5MDAwMTUxO05hTjsxMzk4NTc5NTI0MjMwO3wxMTExMTEwMDEwfDIwNjU4NTMzMzMyMzcuNjQzOzk1OTgyOTkwNzQ0MTMuNjU0OzI1OzE2MzI7MjU7ODk2MjstMTstMTszOTA0NDc4MzQ0NTI2NDMzMDAwMDswOzA7MDswOzA7MzswOzA7MDswOzA7MDswOzswOzA7fDEyNCwxMjQsMjQ3MTIyNjY2MDIsMDsxMDMsMTAzLDQ3NDIwMjYzNTIsMDs4OCw4OCwxNzM0MDk2MjgsMDs3Niw3NiwzNDY4NDgwMzIsMDs2OCw2OCwxMTQ3MTYxODgwLDA7NjEsNjEsMjIxOTkyNDYxMywwOzUxLDUxLDY0MTA3MDk4MjIsMDs1Nyw1NywxNjQzMjU1MjQ0MTYsMDs0MCw0MCw4NDAxNTMxNzc4MzQsMDsyNiwyNiwyMTM4MjM4NDc2MTk2LDA7MTcsMTcsNjQxNTgyOTU0OTAwOSwwO3w0NTAzNTk5NjI3MzcwNDk1OzMzNzcyMDA0MzA1Nzk3MTE7MjI1MTgwMDYxODk2OTYwNzszOTQwNjUyNDkyNTMxNzEzOzIyNTE3OTk4MjAxMDc5MTk7MjI1MTc5OTgxNDIwOTAyNTsyMjUxNzk5ODEzNjg1MjQ5OzIyNTE3OTk4NDcyNDE3NzU7NTI0Mjg5fDQ1MDM1OTk2MjczNzA0OTU7MjI2MDUyNjY0ODI2MjY1NTszNTg4OTY5NTU2MDE3NjYzOzYyMDc%3D%21END%21
 wrong wrinkler pop message
 MS4wNDY1fHwxMzk4NTc5MDAwMTUxO05hTjsxNDA2NjYzNDMyNTQ5O0FtdXhpeHwxMTExMTEwMDEwMHw0MDE3OTg5Nzk4NTI0NTUzMDsxMTI3MTEwMTQ0MDAzNDc5NTA7MzI3OzE2Mzk7MTAxMTY2MTcyNjA3ODE1ODQwOzg5Njg7LTE7LTE7MzkwNDQ3ODM0NDUyNjQzMzAwMDA7MTswOzA7NzE7Mzk2NjszOzc7MTIyNTgwNzQyODE5MTEuNzU7MDswOzA7MDswO2Vhc3RlcjsyMTMwMTczMDY1NTY4MjsyO3wxNzcsMTc3LDE3MTE0MDczNjM1OTk4LDA7MjAwLDIwMCwxNTUwMzQxMjc2NDQxMCwwOzEzOSwxMzksMzQ2NzAwNzAxNTksMDsxMjYsMTI2LDg1OTEyNjExODg2LDA7MTE4LDExOCwyODM5MjcyNTQzMzYsMDsxMTAsMTEwLDY4NjE1ODE5ODUxMCwwOzEwMiwxMDIsMTI2Nzk4Nzk1MTAyMCwwOzEwMywxMDMsNDM2MDkxNTQyNzE1MTksMDsxMDAsMTAwLDIyMDI4OTI2OTQ3NDkxNSwwOzc3LDc3LDE4NDQ5MDg1NTczNjMxMjMsMDs3Myw3Myw4NjcyMDg0MDg1OTI2MTc2LDA7fDQ1MDM1OTk2MjczNzA0OTU7NDUwMzU5OTYyNzM3MDQ5NTsyMjYwNTk1OTA2NzA3NDU1OzQ1MDM1NzM3NDg1NDYwNDc7MjQ0MDUwMzQ5NDg4MDI1NTsyMjUxODI1NTg0MDEyOTcxOzIyNTE3OTk4MTM2ODUyNTk7MjI1MTkzNTEzODcyNDM1MTsyMjUxNzk5ODEzNjg1MjQ5OzMyNzY5fDQ1MDM1OTk2MjczNzA0OTU7MjI2MDUyNjY0ODI2MjY1NTszNTg4OTY5NTU2MDE3NjYzOzYyOTM1Njc%3D%21END%21
 Test reset
 MS4wNDY1fHwxNDEwMzE0MzkxOTQyO05hTjsxNDEwOTkyMjc0OTc4O0FtdXhpeHwxMTExMTExMDEwMHwzNzYxMzAwMDM0NTk0OTcyMDAwOzE0MTYzNDI5NzQ5Njc0ODQwMDAwMDszMDQ7MTg4NTs5NzE2MjQ1MDA1MjQ4NTY4MDA7MTM4NDg7LTE7LTE7MTA1Nzg2Njk4NDg1NDczNzAwMDAwOzM7MDswOzA7LTE7MjE7NTY7MTY1NzI3MzQxODIzMzkyMTUwMDA7MTM3OzA7MDswOzA7ZWFzdGVyOzMwNTU1MzA5NjgxMDI5MjYwMDAwOzEwO3wyNjAsMjYwLDQyNjEyNzMxNTY4Mzk3MzAwMCwwOzI1NSwyNTUsODM3OTk0NDk0MDU0NTQ1OTAwLDA7MjIxLDIyMSw0Mjc5MTUwOTY2NDc1MSwwOzIwOCwyMDgsMTA2MzEwMDk0NjUwOTk3LDA7MjAwLDIwMCwyOTg4MDUzNjA4Mzg4OTQsMDsxOTAsMTkwLDQ4NjcxMzA0ODY3NjgwNiwwOzE3OCwxNzgsMTc1NzYwNDg3OTA2Nzc3MSwwOzIwMCwyMDAsNTIxMjQ1Njg3NDc4ODEzNTAsMDsxNDksMTQ5LDMxNjU1ODA1NTgzNzk4MjkwMCwwOzE1MCwxNTAsMzA3NDYxMzY1OTQxNzE3OTAwMCwwOzEzNSwxMzUsMjg3NjAwOTU5NDU4NDMwMjAwMDAsMDt8NDUwMzU5OTYyNzM3MDQ5NTs0NTAzNTk5NjI3MzcwNDk1OzMzNzc2OTk3MjA1Mjc4NzE7NDUwMzU3Mzc0ODY0NDM1MTsyODE0NzQ5NzY3MTA2NTU5OzIyNTE4MjU1ODQwMTMzMTE7MjI1MTc5OTgxMzY4NTI3OTsyNjgyODA2NTI2Mjk2MDYzOzIyNjA3NzkxMjI0MjU4MjU7NDQ1NDV8NDUwMzU5OTYyNzM3MDQ5NTsyMzkyNDY4MDQzNTk1Nzc1OzM1ODkyNDQ5NzkxODc3MTE7NzIxNTIzMQ%3D%3D%21END%21
 */
/*global Game: false*/
//This script uses functions and parts of functions from the game, Those are not copyrighted by this script in any way

var MAIN_INTERVAL;
var SAVE_INTERVAL;
var NOTE_UPDATE_INTERVAL;

var STOPPED = 1;
var CURRENT_SESSION;
var RESERVE;
var NEXT_BUY;
var Upgrades = [];
var PRODUCTION_LOG = {
    times: [],
    productions: [],
    add: function() {
        this.times.push(new Date());
        this.productions.push(Game.cookiesPs);
        }
    };

const GAME_WIN_FUNCTION = Game.Win;
const GAME_ASCEND_FUNCTION = Game.Ascend;
const GAME_REINCARNATE_FUNCTION = Game.Reincarnate;
var GAME_LOOP_FUNCTION = Game.Loop.toString();
GAME_LOOP_FUNCTION = GAME_LOOP_FUNCTION.replace('Game.accumulatedDelay=Math.min(Game.accumulatedDelay,1000*5);', '');
GAME_LOOP_FUNCTION = new Function(GAME_LOOP_FUNCTION.substring(GAME_LOOP_FUNCTION.indexOf('{')+1,GAME_LOOP_FUNCTION.lastIndexOf('}')));
Game.Loop = GAME_LOOP_FUNCTION;

const CLICKS_PER_SEC = 5;

const SESSION_TIME_STORAGE_NAME = "AutoCookieSessionTime";
const NOTE_AREA_ID = "AutoCookieNotesArea";
const NEXT_BUY_NOTE_ID = "NextBuyNote";
const GOAL_NOTE_ID = "GoalNote";
const RESERVE_NOTE_ID = "ReserveNote";
const SPAWN_WINDOW_NOTE_ID = "SpawnWindowNote";

const CURSOR_BASE_CPS = 0.1;
const GRANDMA_BASE_CPS = 1;
const FARM_BASE_CPS = 8;
const MINE_BASE_CPS = 47;
const FACTORY_BASE_CPS = 260;
const BANK_BASE_CPS = 1400;
const TEMPLE_BASE_CPS = 7800;
const WIZARD_TOWER_BASE_CPS = 44E3;
const SHIPMENT_BASE_CPS = 260E3;
const ALCHEMY_LAB_BASE_CPS = 1.6E6;
const PORTAL_BASE_CPS = 10E6;
const TIME_MACHINE_BASE_CPS = 65E6;
const ANTIMATTER_CONDENSER_BASE_CPS = 430E6;
const PRISM_BASE_CPS = 2.9E9;

const CURSOR = Game.Objects.Cursor;
const GRANDMA = Game.Objects.Grandma;
const FARM = Game.Objects.Farm;
const MINE = Game.Objects.Mine;
const FACTORY = Game.Objects.Factory;
const BANK = Game.Objects.Bank;
const TEMPLE = Game.Objects.Temple;
const WIZARD_TOWER = Game.Objects["Wizard tower"];
const SHIPMENT = Game.Objects.Shipment;
const ALCHEMY_LAB = Game.Objects["Alchemy lab"];
const PORTAL = Game.Objects.Portal;
const TIME_MACHINE = Game.Objects["Time machine"];
const ANTIMATTER_CONDENSER = Game.Objects["Antimatter condenser"];
const PRISM = Game.Objects.Prism;


/*******************************************************************************GENERAL******************************************************************************/
/*
 * Creates a string with the local time
 * @return {string} Text with local time
 */
function getTime() {
    var time = "";
    var currentTime = new Date();
    var hours = currentTime.getHours();
    var minutes = currentTime.getMinutes();
    var seconds = currentTime.getSeconds();
    if (minutes < 10) minutes = "0" + minutes;
    if (seconds < 10) seconds = "0" + seconds;
    time += hours + ":" + minutes + ":" + seconds;
    return time;
}

/*
 * Logs text to chat with the local time
 * @param {string} string Text to log
 */
function log(string) {
    var time = getTime();
    console.log("[AutoCookie " + time + "] " + string);
}

function timeString(seconds) {
    //Writes a string saying time
    var remainingSeconds, showString = "";
    remainingSeconds = seconds;
    var years = Math.floor(remainingSeconds / (365 * 24 * 60 * 60));
    if (years > 0) {
        var yearString = 'years ';
        if (years === 1) yearString = 'year ';
        showString += years + yearString;
        remainingSeconds -= years * 365 * 24 * 60 * 60;
    }

    var months = Math.floor(remainingSeconds / (30 * 24 * 60 * 60));
    if (months > 0) {
        var monthString = 'months ';
        if (months === 1) monthString = 'month ';
        showString += months + monthString;
        remainingSeconds -= months * 30 * 24 * 60 * 60;
    }

    var weeks = Math.floor(remainingSeconds / (7 * 24 * 60 * 60));
    if (weeks > 0) {
        showString += weeks + "w ";
        remainingSeconds -= weeks * 7 * 24 * 60 * 60;
    }

    var days = Math.floor(remainingSeconds / (24 * 60 * 60));
    if (days > 0) {
        showString += days + "d ";
        remainingSeconds -= days * 24 * 60 * 60;
    }

    var hours =  Math.floor(remainingSeconds / (60 * 60));
    if (hours > 0) {
        showString += hours + "h ";
        remainingSeconds -= hours * 60 * 60;
    }

    var minutes = Math.floor(remainingSeconds / 60);
    if (minutes > 0) {
        showString += minutes + "m ";
        remainingSeconds -= minutes * 60;
    }

    if (remainingSeconds > 1 || (minutes + hours + days === 0))
        showString += Math.floor(remainingSeconds) + "s";
    return showString;
}

function convertNumeral(number) {
    var last_digit = number.toString()[number.toString().length - 1];
    if (number === 0) return "";
    var string = "th";
    if (last_digit == 1) string = "st";
    if (last_digit == 2) string = "nd";
    if (last_digit == 3) string = "rd";
    if (number > 10) {
        var last_two_digits = number.toString()[number.toString().length - 2] + last_digit;
        if (last_two_digits > 10 && last_two_digits < 14) string = "th";
    }
    return number + string;
}

function round(number, digits) {
    return Math.round(number * Math.pow(10, digits)) / Math.pow(10, digits);
}

/*******************************************************************************BUYABLE******************************************************************************/
function calculatePayback(price, cpsIncrease) {
    var payback;
    payback = price / cpsIncrease;
    if (price > Game.cookies) {
        payback += (price - (Game.cookies - RESERVE.reserveAmount)) / Game.cookiesPs;
    }
    return round(payback, 6);
}

/**
 * @param {number} upgradePrice
 * @param {Map.<Game.Object, number>} requiredBuildings
 * @param {Array.<Upgrade>} requiredUpgrades
 * @returns {number} total price for this upgrade including its requirements
 */
function calculateUpgradePrice(upgradePrice, requiredBuildings, requiredUpgrades) {
    var totalPrice = upgradePrice;
    requiredBuildings.forEach(function(amountNeeded, Building){
       if (amountNeeded > Building.amount) {
           totalPrice += Building.getSumPrice(amountNeeded - Building.amount);
       }
    });
    requiredUpgrades.forEach(function (element) {
        var upgradePrice = Game.Upgrades[element.name].getPrice();
        if (element.requiredBuildings.size == 0 && element.requiredUpgrades.length == 0) { //This upgrade requires nothing.
            totalPrice += upgradePrice;
            return;
        }
        totalPrice += calculateUpgradePrice(upgradePrice, element.requiredBuildings, element.requiredUpgrades);
    });
    return totalPrice;
}

/****************************************************************************CPS INCREASE*****************************************************************************/
/**
 * Calculates the increase in cookies per second from building <param>amount</param> of <param>Building</param>
 * @param {Game.Object} Building we are going to be building
 * @param {Number} amount How many are we building
 * @returns {Number} increase in cookies per second
 */
function getBuildingCPSIncrease(Building, amount) {
    if (Building == Game.Objects.Cursor) {
        return getCursorCPSIncrease(amount, 0); //Cps increase from cursors
    } else {
        var cpsIncrease = getCursorCPSIncrease(0, amount); //Cps increase from non-cursors
    }

    if (Building == Game.Objects.Grandma) {
        return cpsIncrease + calculateGrandmaCPSIncrease(amount, "");
    }

    if (Building == Game.Objects.Mine) {
        return cpsIncrease + calculateMineCPSIncrease(amount, "");
    }

    if (Building == Game.Objects.Farm) {
        return cpsIncrease + calculateFarmCPSIncrease(amount, "");
    }

    if (Building == Game.Objects.Factory) {
        return cpsIncrease + calculateFactoryCPSIncrease(amount, "");
    }

    if (Building == Game.Objects.Bank) {
        return cpsIncrease + calculateBankCPSIncrease(amount, "");
    }

    if (Building == Game.Objects.Temple) {
        return cpsIncrease + calculateTempleCPSIncrease(amount, "");
    }

    if (Building == Game.Objects["Wizard tower"]) {
        return cpsIncrease + calculateWizardTowerCPSIncrease(amount, "");
    }

    if (Building == Game.Objects.Shipment) {
        return cpsIncrease + calculateShipmentCPSIncrease(amount, "");
    }

    if (Building == Game.Objects["Alchemy lab"]) {
        return cpsIncrease + calculateAlchemyLabCPSIncrease(amount, "");
    }

    if (Building == Game.Objects.Portal) {
        cpsIncrease += calculatePortalCPSIncrease(0, "");
    }

    if (Building == Game.Objects["Time machine"]) {
        cpsIncrease += calculateTimeMachineCPSIncrease(0, "");
    }

    if (Building == Game.Objects["Antimatter condenser"]) {
        cpsIncrease += calculateAntimatterCondenserCPSIncrease(0, "");
    }

    if (Building == Game.Objects.Prism) {
        cpsIncrease += calculatePrismCPSIncrease(0, "");
    }

    cpsIncrease += Building.storedCps * Game.globalCpsMult * amount;
    return cpsIncrease;
}

function hasOrIsChoice(upgradeName, choice) {
    return Game.Has(upgradeName) || upgradeName === choice;
}

/**
 * Calculates the CPS gotten from building Farms or one of the non multiplier upgrades
 * @param {number} amount Amount to be bought
 * @param {string} upgradeName Upgrade to calculate increase
 * @return {number} Increase in CPS
 */
function calculateFarmCPSIncrease(amount, upgradeName) {
    upgradeName = upgradeName || "";
    const Building = Game.Objects.Farm;
    var multiplier = 1;
    if (hasOrIsChoice("Farmer grandmas", upgradeName)) {
        multiplier = Game.Objects.Grandma.amount * .01;
    }
    if (hasOrIsChoice("Future almanacs", upgradeName)) {
        multiplier = Game.Objects["Time machine"].amount * .05;
    }
    if (hasOrIsChoice("Rain prayer", upgradeName)) {
        multiplier = Game.Objects.Temple.amount * .05;
    }
    if (hasOrIsChoice("Magical botany", upgradeName)) {
        multiplier = Game.Objects["Wizard tower"].amount * .05;
    }
    if (hasOrIsChoice("Infernal crops", upgradeName)) {
        multiplier = Game.Objects.Portal.amount * .05;
    }
    return Building.storedCps * multiplier * Game.globalCpsMult * amount;
}

/**
 * Calculates the CPS gotten from building Mine or one of the non multiplier upgrades
 * @param {number} amount Amount to be bought
 * @param {string} upgradeName Upgrade to calculate increase
 * @return {number} Increase in CPS
 */
function calculateMineCPSIncrease(amount, upgradeName) {
    upgradeName = upgradeName || "";
    const Building = Game.Objects.Mine;
    var multiplier = 1;
    if (hasOrIsChoice("Miner grandmas", upgradeName)) {
        multiplier = Game.Objects.Grandma.amount / 2 * .01;
    }
    if (hasOrIsChoice("Seismic magic", upgradeName)) {
        multiplier = Game.Objects["Wizard tower"].amount * .05;
    }
    if (hasOrIsChoice("Asteroid mining", upgradeName)) {
        multiplier = Game.Objects.Shipment.amount * .05;
    }
    if (hasOrIsChoice("Fossil fuels", upgradeName)) {
        multiplier = Game.Objects.Shipment.amount * .05;
    }
    if (hasOrIsChoice("Primordial ores", upgradeName)) {
        multiplier = Game.Objects["Alchemy lab"].amount * .05;
    }
    return Building.storedCps * multiplier * Game.globalCpsMult * amount;
}

/**
 * Calculates the CPS gotten from building Factory or one of the non multiplier upgrades
 * @param {number} amount Amount to be bought
 * @param {string} upgradeName Upgrade to calculate increase
 * @return {number} Increase in CPS
 */
function calculateFactoryCPSIncrease(amount, upgradeName) {
    upgradeName = upgradeName || "";
    const Building = Game.Objects.Factory;
    var multiplier = 1;
    if (hasOrIsChoice("Worker grandmas", upgradeName)) {
        multiplier = Game.Objects.Grandma.amount / 3 * .01;
    }
    if (hasOrIsChoice("Quantum electronics", upgradeName)) {
        multiplier = Game.Objects["Antimatter condenser"].amount * .05;
    }
    if (hasOrIsChoice("Temporal overclocking", upgradeName)) {
        multiplier = Game.Objects["Time machine"].amount * .05;
    }
    if (hasOrIsChoice("Printing presses", upgradeName)) {
        multiplier = Game.Objects.Bank.amount * .05;
    }
    if (hasOrIsChoice("Shipyards", upgradeName)) {
        multiplier = Game.Objects.Shipment.amount * .05;
    }
    return Building.storedCps * multiplier * Game.globalCpsMult * amount;
}

/**
 * Calculates the CPS gotten from building Bank or one of the non multiplier upgrades
 * @param {number} amount Amount to be bought
 * @param {string} upgradeName Upgrade to calculate increase
 * @return {number} Increase in CPS
 */
function calculateBankCPSIncrease(amount, upgradeName) {
    upgradeName = upgradeName || "";
    const Building = Game.Objects.Bank;
    var multiplier = 1;
    if (hasOrIsChoice("Banker Grandmas", upgradeName)) {
        multiplier = Game.Objects.Grandma.amount / 4 * .01;
    }
    if (hasOrIsChoice("Contracts from beyond", upgradeName)) {
        multiplier = Game.Objects.Portal.amount * .05;
    }
    if (hasOrIsChoice("Printing presses", upgradeName)) {
        multiplier = Game.Objects.Factory.amount * .001;
    }
    if (hasOrIsChoice("Gold fund", upgradeName)) {
        multiplier = Game.Objects["Alchemy lab"].amount * .05;
    }
    if (hasOrIsChoice("Extra physics funding", upgradeName)) {
        multiplier = Game.Objects["Antimatter condenser"].amount * .05;
    }
    return Building.storedCps * multiplier * Game.globalCpsMult * amount;
}

/**
 * Calculates the CPS gotten from building Temple or one of the non multiplier upgrades
 * @param {number} amount Amount to be bought
 * @param {string} upgradeName Upgrade to calculate increase
 * @return {number} Increase in CPS
 */
function calculateTempleCPSIncrease(amount, upgradeName) {
    upgradeName = upgradeName || "";
    const Building = Game.Objects.Temple;
    var multiplier = 1;
    if (hasOrIsChoice("Priestess Grandmas", upgradeName)) {
        multiplier += Game.Objects.Grandma.amount / 5 * .01;
    }
    if (hasOrIsChoice("Rain prayer", upgradeName)) {
        multiplier += Game.Objects.Farm.amount * .001;
    }
    if (hasOrIsChoice("Paganism", upgradeName)) {
        multiplier += Game.Objects.Portal.amount * .05;
    }
    if (hasOrIsChoice("God particle", upgradeName)) {
        multiplier += Game.Objects["Antimatter condenser"].amount * .05;
    }
    if (hasOrIsChoice("Mystical energies", upgradeName)) {
        multiplier += Game.Objects.Prism.amount * .05;
    }
    return Building.storedCps * multiplier * Game.globalCpsMult * amount;
}

/**
 * Calculates the CPS gotten from building Wizard tower or one of the non multiplier upgrades
 * @param {number} amount Amount to be bought
 * @param {string} upgradeName Upgrade to calculate increase
 * @return {number} Increase in CPS
 */
function calculateWizardTowerCPSIncrease(amount, upgradeName) {
    upgradeName = upgradeName || "";
    const Building = Game.Objects["Wizard tower"];
    var multiplier = 1;
    if (hasOrIsChoice("Witch grandmas", upgradeName)) {
        multiplier += Game.Objects.Grandma.amount / 6 * .01;
    }
    if (hasOrIsChoice("Seismic magic", upgradeName)) {
        multiplier += Game.Objects.Mine.amount * .001;
    }
    if (hasOrIsChoice("Arcane knowledge", upgradeName)) {
        multiplier += Game.Objects["Alchemy lab"].amount * .05;
    }
    if (hasOrIsChoice("Magical botany", upgradeName)) {
        multiplier += Game.Objects.Farm.amount * .001;
    }
    if (hasOrIsChoice("Light magic", upgradeName)) {
        multiplier += Game.Objects.Prism.amount * .05;
    }
    return Building.storedCps * multiplier * Game.globalCpsMult * amount;
}

/**
 * Calculates the CPS gotten from building Shipment or one of the non multiplier upgrades
 * @param {number} amount Amount to be bought
 * @param {string} upgradeName Upgrade to calculate increase
 * @return {number} Increase in CPS
 */
function calculateShipmentCPSIncrease(amount, upgradeName) {
    upgradeName = upgradeName || "";
    const Building = Game.Objects.Shipment;
    var multiplier = 1;
    if (hasOrIsChoice("Cosmic grandmas", upgradeName)) {
        multiplier += Game.Objects.Grandma.amount / 7 * .01;
    }
    if (hasOrIsChoice("Seismic magic", upgradeName)) {
        multiplier += Game.Objects.Mine.amount * .001;
    }
    if (hasOrIsChoice("Arcane knowledge", upgradeName)) {
        multiplier += Game.Objects["Alchemy lab"].amount * .05;
    }
    if (hasOrIsChoice("Magical botany", upgradeName)) {
        multiplier += Game.Objects.Farm.amount * .001;
    }
    if (hasOrIsChoice("Light magic", upgradeName)) {
        multiplier += Game.Objects.Prism.amount * .05;
    }
    return Building.storedCps * multiplier * Game.globalCpsMult * amount;
}

/**
 * Calculates the CPS gotten from building Alchemy lab or one of the non multiplier upgrades
 * @param {number} amount Amount to be bought
 * @param {string} upgradeName Upgrade to calculate increase
 * @return {number} Increase in CPS
 */
function calculateAlchemyLabCPSIncrease(amount, upgradeName) {
    upgradeName = upgradeName || "";
    const Building = Game.Objects["Alchemy lab"];
    var multiplier = 1;
    if (hasOrIsChoice("Transmuted grandmas", upgradeName)) {
        multiplier += Game.Objects.Grandma.amount / 8 * .01;
    }
    if (hasOrIsChoice("Arcane knowledge", upgradeName)) {
        multiplier += Game.Objects["Wizard tower"].amount * .001;
    }
    if (hasOrIsChoice("Primordial ores", upgradeName)) {
        multiplier += Game.Objects.Mine.amount * .001;
    }
    if (hasOrIsChoice("Gold fund", upgradeName)) {
        multiplier += Game.Objects.Bank.amount * .001;
    }
    if (hasOrIsChoice("Chemical proficiency", upgradeName)) {
        multiplier += Game.Objects["Antimatter condenser"].amount * .05;
    }
    return Building.storedCps * multiplier * Game.globalCpsMult * amount;
}

/**
 * Calculates the CPS gotten from building Portal or one of the non multiplier upgrades
 * @param {number} amount Amount to be bought
 * @param {string} upgradeName Upgrade to calculate increase
 * @return {number} Increase in CPS
 */
function calculatePortalCPSIncrease(amount, upgradeName) {
    upgradeName = upgradeName || "";
    const Building = Game.Objects.Portal;
    var multiplier = 1;
    if (hasOrIsChoice("Altered grandmas", upgradeName)) {
        multiplier += Game.Objects.Grandma.amount / 9 * .01;
    }
    if (hasOrIsChoice("Contracts from beyond", upgradeName)) {
        multiplier += Game.Objects.Bank.amount * .001;
    }
    if (hasOrIsChoice("Paganism", upgradeName)) {
        multiplier += Game.Objects.Temple.amount * .001;
    }
    if (hasOrIsChoice("Infernal crops", upgradeName)) {
        multiplier += Game.Objects.Farm.amount * .001;
    }
    if (hasOrIsChoice("Abysmal glimmer", upgradeName)) {
        multiplier += Game.Objects.Prism.amount * .05;
    }
    return Building.storedCps * multiplier * Game.globalCpsMult * amount;
}

/**
 * Calculates the CPS gotten from building Time machine or one of the non multiplier upgrades
 * @param {number} amount Amount to be bought
 * @param {string} upgradeName Upgrade to calculate increase
 * @return {number} Increase in CPS
 */
function calculateTimeMachineCPSIncrease(amount, upgradeName) {
    upgradeName = upgradeName || "";
    const Building = Game.Objects["Time machine"];
    var multiplier = 1;
    if (hasOrIsChoice("Grandmas\' grandmas", upgradeName)) {
        multiplier += Game.Objects.Grandma.amount / 10 * .01;
    }
    if (hasOrIsChoice("Future almanacs", upgradeName)) {
        multiplier += Game.Objects.Farm.amount * .001;
    }
    if (hasOrIsChoice("Temporal overclocking", upgradeName)) {
        multiplier += Game.Objects.Factory.amount * .001;
    }
    if (hasOrIsChoice("Relativistic parsec-skipping", upgradeName)) {
        multiplier += Game.Objects.Shipment.amount * .001;
    }
    if (hasOrIsChoice("Primeval glow", upgradeName)) {
        multiplier += Game.Objects.Prism.amount * .05;
    }
    return Building.storedCps * multiplier * Game.globalCpsMult * amount;
}

/**
 * Calculates the CPS gotten from building Antimatter Condenser or one of the non multiplier upgrades
 * @param {number} amount Amount to be bought
 * @param {string} upgradeName Upgrade to calculate increase
 * @return {number} Increase in CPS
 */
function calculateAntimatterCondenserCPSIncrease(amount, upgradeName) {
    upgradeName = upgradeName || "";
    const Building = Game.Objects["Antimatter condenser"];
    var multiplier = 1;
    if (hasOrIsChoice("Antigrandmas", upgradeName)) {
        multiplier += Game.Objects.Grandma.amount / 11 * .01;
    }
    if (hasOrIsChoice("Quantum electronics", upgradeName)) {
        multiplier += Game.Objects.Factory.amount * .001;
    }
    if (hasOrIsChoice("God particle", upgradeName)) {
        multiplier += Game.Objects.Temple.amount * .001;
    }
    if (hasOrIsChoice("Extra physics funding", upgradeName)) {
        multiplier += Game.Objects.Bank.amount * .001;
    }
    if (hasOrIsChoice("Chemical proficiency", upgradeName)) {
        multiplier += Game.Objects["Alchemy lab"].amount * .001;
    }
    return Building.storedCps * multiplier * Game.globalCpsMult * amount;
}

/**
 * Calculates the CPS gotten from building Prism or one of the non multiplier upgrades
 * @param {number} amount Amount to be bought
 * @param {string} upgradeName Upgrade to calculate increase
 * @return {number} Increase in CPS
 */
function calculatePrismCPSIncrease(amount, upgradeName) {
    upgradeName = upgradeName || "";
    const Building = Game.Objects.Prism;
    var multiplier = 1;
    if (hasOrIsChoice("Rainbow grandmas", upgradeName)) {
        multiplier += Game.Objects.Grandma.amount / 12 * .01;
    }
    if (hasOrIsChoice("Abysmal glimmer", upgradeName)) {
        multiplier += Game.Objects.Portal.amount * .001;
    }
    if (hasOrIsChoice("Primeval glow", upgradeName)) {
        multiplier += Game.Objects["Time machine"].amount * .001;
    }
    if (hasOrIsChoice("Light magic", upgradeName)) {
        multiplier += Game.Objects["Wizard tower"].amount * .001;
    }
    if (hasOrIsChoice("Mystical energies", upgradeName)) {
        multiplier += Game.Objects.Temple.amount * .001;
    }
    return Building.storedCps * multiplier * Game.globalCpsMult * amount;
}

/**
 * @returns {number} Amount of non cursor buildings.
 */
function getAmountOfNonCursors() {
    var num = 0;
    for (var i in Game.Objects) num += Game.Objects[i].amount;
    return num - Game.Objects.Cursor.amount;
}

function calculateMouseCpsIncrease(mouseUpgradeName) {
    var add = 0; //Increase to base cursor cps and mouse clicks per non cursor building
    if (hasOrIsChoice("Thousand fingers", mouseUpgradeName)) add += 0.1;
    if (hasOrIsChoice("Million fingers", mouseUpgradeName)) add += 0.5;
    if (hasOrIsChoice("Billion fingers", mouseUpgradeName)) add += 5;
    if (hasOrIsChoice("Trillion fingers", mouseUpgradeName)) add += 50;
    if (hasOrIsChoice("Quadrillion fingers", mouseUpgradeName)) add += 500;
    if (hasOrIsChoice("Quintillion fingers", mouseUpgradeName)) add += 5e3;
    if (hasOrIsChoice("Sextillion fingers", mouseUpgradeName)) add += 5e4;
    if (hasOrIsChoice("Septillion fingers", mouseUpgradeName)) add += 5e5;
    if (hasOrIsChoice("Octillion fingers", mouseUpgradeName)) add += 5e6;
    add *= getAmountOfNonCursors();
    var cps = Game.cookiesPs;
    if (hasOrIsChoice("Plastic mouse", mouseUpgradeName)) add += cps * 0.01;
    if (hasOrIsChoice("Iron mouse", mouseUpgradeName)) add += cps * 0.01;
    if (hasOrIsChoice("Titanium mouse", mouseUpgradeName)) add += cps * 0.01;
    if (hasOrIsChoice("Adamantium mouse", mouseUpgradeName)) add += cps * 0.01;
    if (hasOrIsChoice("Unobtainium mouse", mouseUpgradeName)) add += cps * 0.01;
    if (hasOrIsChoice("Eludium mouse", mouseUpgradeName)) add += cps * 0.01;
    if (hasOrIsChoice("Wishalloy mouse", mouseUpgradeName)) add += cps * 0.01;
    if (hasOrIsChoice("Fantasteel Mouse", mouseUpgradeName)) add += cps * 0.01;
    if (hasOrIsChoice("Nevercrack Mouse", mouseUpgradeName)) add += cps * 0.01;
    var baseCookiesPerClick = 1;
    var multiplier = 1;
    if (hasOrIsChoice("Reinforced index finger", mouseUpgradeName)) multiplier *= 2;
    if (hasOrIsChoice("Carpal tunnel prevention cream", mouseUpgradeName)) multiplier *= 2;
    if (hasOrIsChoice("Ambidextrous", mouseUpgradeName)) multiplier *= 2;
    var cookie_duration = 13;
    if (Game.Has("Get lucky")) cookie_duration *= 2;
    return ((baseCookiesPerClick * multiplier) + add - Game.mouseCps()) * CLICKS_PER_SEC * cookie_duration;
}

/**
 * Calculates the CPS gotten from building Grandmas or one of the non multiplier upgrades
 * @param {number} amount Amount to be bought
 * @param {string} upgradeName Upgrade to be bought
 * @return {number} Increase in CPS
 */
function calculateGrandmaCPSIncrease(amount, upgradeName) {
    upgradeName = upgradeName || "";
    var multiplierPower = 0;
    if (hasOrIsChoice("Farmer grandmas", upgradeName)) multiplierPower++;
    if (hasOrIsChoice("Miner grandmas", upgradeName)) multiplierPower++;
    if (hasOrIsChoice("Worker grandmas", upgradeName)) multiplierPower++;
    if (hasOrIsChoice("Banker grandmas", upgradeName)) multiplierPower++;
    if (hasOrIsChoice("Priestess grandmas", upgradeName)) multiplierPower++;
    if (hasOrIsChoice("Witch grandmas", upgradeName)) multiplierPower++;
    if (hasOrIsChoice("Cosmic grandmas", upgradeName)) multiplierPower++;
    if (hasOrIsChoice("Transmuted grandmas", upgradeName)) multiplierPower++;
    if (hasOrIsChoice("Altered grandmas", upgradeName)) multiplierPower++;
    if (hasOrIsChoice("Grandmas\' grandmas", upgradeName)) multiplierPower++;
    if (hasOrIsChoice("Antigrandmas", upgradeName)) multiplierPower++;
    if (hasOrIsChoice("Rainbow grandmas", upgradeName)) multiplierPower++;

    if (hasOrIsChoice("Bingo center/Research facility", upgradeName)) multiplierPower += 2;
    if (hasOrIsChoice("Ritual rolling pins", upgradeName)) multiplierPower++;
    if (hasOrIsChoice("Forwards from grandma", upgradeName)) multiplierPower++;
    if (hasOrIsChoice("Steel-plated rolling pins", upgradeName)) multiplierPower++;
    if (hasOrIsChoice("Lubricated dentures", upgradeName)) multiplierPower++;
    if (hasOrIsChoice("Prune juice", upgradeName)) multiplierPower++;
    if (hasOrIsChoice("Double-thick glasses", upgradeName)) multiplierPower++;
    if (hasOrIsChoice("Aging agents", upgradeName)) multiplierPower++;
    if (hasOrIsChoice("Xtreme walkers", upgradeName)) multiplierPower++;
    if (hasOrIsChoice("The Unbridling", upgradeName)) multiplierPower++;
    var baseIncrease = 0;
    const newGrandmas = Game.Objects.Grandma.amount + amount;
    if (hasOrIsChoice("One mind", upgradeName)) baseIncrease += newGrandmas * 0.02;
    if (hasOrIsChoice("Communal brainsweep", upgradeName)) baseIncrease += newGrandmas * 0.02;
    if (hasOrIsChoice("Elder Pact", upgradeName)) baseIncrease += Game.Objects.Portal.amount * 0.05;
    var baseCps = GRANDMA_BASE_CPS + baseIncrease;
    var total_cps = baseCps * Math.pow(2, multiplierPower) * newGrandmas;
    return (total_cps - Game.Objects.Grandma.storedTotalCps) * Game.globalCpsMult;
}

/**
 * Calculates the CPS increase of getting cursors and non-cursors
 * @param {number} amount Number of cursors to add
 * @param {number} non_cursor Number of non_cursor to add
 * @return {number} Increase in CPS
 */
function getCursorCPSIncrease(amount, non_cursor) {
    var add = 0;
    if (Game.Has("Thousand fingers")) add += 0.1;
    if (Game.Has("Million fingers")) add += 0.5;
    if (Game.Has("Billion fingers")) add += 5;
    if (Game.Has("Trillion fingers")) add += 50;
    if (Game.Has("Quadrillion fingers")) add += 500;
    if (Game.Has("Quintillion fingers")) add += 5e3;
    if (Game.Has("Sextillion fingers")) add += 5e4;
    if (Game.Has("Septillion fingers")) add += 5e5;
    if (Game.Has("Octillion fingers")) add += 5e6;
    if (add > 0) {
        for (var i in Game.Objects) {
            if (Game.Objects[i].name != "Cursor") {
                non_cursor += Game.Objects[i].amount;
            }
        }
        add *= non_cursor;
    }
    var total_cps = Game.ComputeCps(CURSOR_BASE_CPS, Game.Has("Reinforced index finger") + Game.Has("Carpal tunnel prevention cream") + Game.Has("Ambidextrous"), add) * (Game.Objects.Cursor.amount + amount);
    return (total_cps - Game.Objects.Cursor.storedTotalCps) * Game.globalCpsMult;
}

/**
 * @param {Game.Object} AffectedBuilding Building this upgrade affects
 * @param {Map.<Game.Object, number>} requirements Map of Buildings and the needed amount required for this upgrade
 * @param {Achievement} Achievement Best achievement gotten from this upgrade.
 * @param multiplier Multiplier to apply to the production of the affected building
 * @returns {number} How much will this upgrade increase the CPS
 */
function calculateUpgradeCPSIncrease(AffectedBuilding, requirements, Achievement, multiplier) {
    var cps_increase = 0;
    var affectedBuildingRequired = 0;
    if (typeof requirements !== 'undefined') {
        requirements.forEach(function (buildingsNeeded, Building) {
            if (buildingsNeeded > Building.amount) {
                var required = buildingsNeeded - Building.amount;
                if (Building == AffectedBuilding) {
                    affectedBuildingRequired = required;
                }
                cps_increase += getBuildingCPSIncrease(Building, required);
            }
        });
    }

    cps_increase += AffectedBuilding.storedCps * (AffectedBuilding.amount + affectedBuildingRequired) * (multiplier - 1) * Game.globalCpsMult;
    cps_increase += getTotalAchievementsToTarget(Achievement) * calculateAchievementCpsIncrease();
    return cps_increase;
}

function calculateHeavenlyKittenCPSIncrease(upgradeName) {
    //Calculates payback for kitten upgrades
    var base_cps = Game.cookiesPs / Game.globalCpsMult;
    var multiplier = Game.globalCpsMult / getKittenMultiplier(Game.milkProgress);
    var heavenlyChipMultiplier = 0;
    if (upgradeName == "Heavenly chip secret" && !Game.Has("Heavenly chip secret")) heavenlyChipMultiplier += 0.05;
    if (upgradeName == "Heavenly cookie stand" && !Game.Has("Heavenly cookie stand")) heavenlyChipMultiplier += 0.20;
    if (upgradeName == "Heavenly bakery" && !Game.Has("Heavenly bakery")) heavenlyChipMultiplier += 0.25;
    if (upgradeName == "Heavenly confectionery" && !Game.Has("Heavenly confectionery")) heavenlyChipMultiplier += 0.25;
    if (upgradeName == "Heavenly key" && !Game.Has("Heavenly key")) heavenlyChipMultiplier += 0.25;
    multiplier += Game.prestige * 0.01 * heavenlyChipMultiplier;
    multiplier *= getKittenMultiplier(Game.milkProgress, upgradeName);
    return Math.max((base_cps * multiplier) - Game.cookiesPs, 0);
}

function getKittenMultiplier(milk, choice) {
    var multiplier = 1;
    if (hasOrIsChoice("Kitten helpers", choice)) multiplier *= ( 1 + milk * 0.1);
    if (hasOrIsChoice("Kitten workers", choice)) multiplier *= ( 1 + milk * 0.125);
    if (hasOrIsChoice("Kitten engineers", choice)) multiplier *= ( 1 + milk * 0.15);
    if (hasOrIsChoice("Kitten overseers", choice)) multiplier *= ( 1 + milk * 0.175);
    if (hasOrIsChoice("Kitten managers", choice)) multiplier *= ( 1 + milk * 0.2);
    if (hasOrIsChoice("Kitten accountants", choice)) multiplier *= ( 1 + milk * 0.2);
    if (hasOrIsChoice("Kitten specialists", choice)) multiplier *= ( 1 + milk * 0.2);
    if (hasOrIsChoice("Kitten experts", choice)) multiplier *= ( 1 + milk * 0.2);
    if (hasOrIsChoice("Kitten angels", choice)) multiplier *= ( 1 + milk * 0.1);
    return multiplier;
}

function calculateAchievementCpsIncrease() {
    var multiplier = Game.globalCpsMult / getKittenMultiplier(Game.milkProgress);
    multiplier *= getKittenMultiplier(Game.milkProgress + 0.04);
    var baseCps = Game.cookiesPs / Game.globalCpsMult;
    return (baseCps * multiplier) - Game.cookiesPs;
}

/**
 * Compares required buildings with amount gotten, number of buildings required is lost!
 * @param {Map.<Game.Object, number>} buildingRequirements
 * @returns {Array.<Building>} List of buildings still needed.
 */
function buildingsRequired(buildingRequirements) {
    var requirements = [];
    buildingRequirements.forEach(function (amountNeeded, gameBuilding) {
        if (amountNeeded > gameBuilding.amount) {
            requirements.push(new Building(gameBuilding));
        }
    });
    return requirements;
}

function upgradesRequired(upgradeRequirements) {
    var requirements = [];
    upgradeRequirements.forEach(function (Upgrade) {
        if (Upgrade.add == true) {
            requirements.push(Upgrade);
        }
    });
    return requirements;
}

/**
 *
 * @param {Map.<Game.Object, number>} buildingRequirements
 * @param {Array.<Upgrade>} upgradeRequirements
 * @returns {Building} Best requirement or undefined if all requirements are met
 */
function bestRequirement(buildingRequirements, upgradeRequirements) {
    if (buildingRequirements.size == 0 && upgradeRequirements.length == 0) return undefined; //There are no requirements
    var requirements = [];
    //value is a building, key is amount required of that building.
    if (buildingRequirements.size > 0) {
        requirements = requirements.concat(buildingsRequired(buildingRequirements));
    }
    if (upgradeRequirements.length > 0) {
        upgradeRequirements.forEach(function (Upgrade) {
            var requiredBuildings = buildingsRequired(Upgrade.requiredBuildings); //Remaining required buildings from original list
            var requiredUpgrades = upgradesRequired(Upgrade.requiredUpgrades);
            if (requiredBuildings.length == 0 && requiredUpgrades.length == 0 && Upgrade.add == true) { //This upgrade requires nothing.
                requirements.push(Upgrade);
                return;
            }
            if (requiredBuildings.length > 0 || requiredUpgrades.length > 0) { //Building requires something
                requirements.push(bestRequirement(Upgrade.requiredBuildings, Upgrade.requiredUpgrades));
            }
        });
    }
    return calculateBestBuy(requirements);
}


/**
 * Wraps a game building in a buyable.
 * @param {Game.Object} GameObject Game Object to copy data from
 * @return {Building} Buyable instace for the given Building
 */
function Building(GameObject) {
    this.name = GameObject.name;
    this.price = GameObject.getPrice();
    this.nextBuy = GameObject; //This is what we will buy next
    const cpsIncrease = getBuildingCPSIncrease(GameObject, 1); //CPS increase from building one of these buildings
    this.payback = calculatePayback(this.price, cpsIncrease);
    this.add = true; //This should be considered to buy.
}

/**
 * Wraps a game upgrade in a buyable.
 * @param {Game.Upgrade} gameUpgrade Upgrade we are wrapping
 * @param {Map.<Game.Object, number>} buildingRequirements Map of buildings needed and the amount.
 * @param {Array.<Upgrade>} upgradeRequirements Upgrades required
 * @param {Game.Object} AffectedBuilding Building this upgrade affects.
 * @param {Game.Achievement} Achievement Achievement unlocked if upgrade is bought
 * @param {number} multiplier Multiplier of this upgrade
 * @param {number} power Power of this upgrade for upgrades similar to cookies
 * @return {Upgrade} Buyable instance for the given Upgrade
 */
function Upgrade(gameUpgrade, AffectedBuilding, buildingRequirements, upgradeRequirements, Achievement, multiplier, power) {
    multiplier = multiplier || 2;
    this.name = gameUpgrade.name;
    this.requiredBuildings = buildingRequirements || new Map();
    this.requiredUpgrades = upgradeRequirements || [];
    Upgrades[this.name] = this;
    //gameUpgrade.pool;//can be "", cookie, toggle, debug, prestige, prestigeDecor, tech, or unused
    if (gameUpgrade.bought === 0) { //Upgrade still not bought
        this.add = true; //This should be considered to buy.
        var totalPrice = calculateUpgradePrice(gameUpgrade.getPrice(), this.requiredBuildings, this.requiredUpgrades);
        var requirement = bestRequirement(this.requiredBuildings, this.requiredUpgrades);
        this.nextBuy = typeof requirement === 'undefined' ? gameUpgrade : requirement.nextBuy;
        //Either a requirement if this upgrades needs it to be unlocked or the upgrade itself.
        this.price = this.nextBuy.getPrice();
        if (AffectedBuilding) { //Building Upgrades
            var cpsIncrease = calculateUpgradeCPSIncrease(AffectedBuilding, this.requiredBuildings, Achievement, multiplier);
        } else {
            if ((gameUpgrade.pool == "cookie" || power)) { //Cookie Upgrades and some research
                if (typeof gameUpgrade.power == 'function' || typeof power == 'function' ) {
                    log(gameUpgrade.name + " has power as function");
                }
                power = power || gameUpgrade.power; //If power is 0 it will be set to Upgrade.power
                cpsIncrease = Game.cookiesPs * (power / 100); //calculateCookieCPSIncrease(power);
            } else if (/Kitten|Heavenly/.test(gameUpgrade.name)) { //Kitten and Heavenly chip Upgrades
                cpsIncrease = calculateHeavenlyKittenCPSIncrease(gameUpgrade.name);
            } else if (/mouse/.test(gameUpgrade.name)) { //Mouse Upgrades
                cpsIncrease = calculateMouseCpsIncrease(gameUpgrade.name);
            } else if (gameUpgrade.pool == "tech") { //Remaining Bingo Upgrades
                cpsIncrease = calculateGrandmaCPSIncrease(0, gameUpgrade.name);
            } else {
                log("Uncaught upgrade: " + gameUpgrade.name);
            }
        }
        this.payback = calculatePayback(totalPrice, cpsIncrease);
    }
}

/**
 * Wraps a game achievement in a buyable.
 * @param {Game.Achievement} Achievement Achievement we are wrapping
 * @param {Map.<Game.Object, number>} requirements Map of buildings needed and the amount.
 * @param {Achievement} AchievementRequired Required achievement to unlock this one.
 * @return {Achievement} Buyable instace for the given Achievement
 */
function Achievement(Achievement, requirements, AchievementRequired) {
    this.name = Achievement.name;
    if (Achievement.won === 0) {
        var requirement = bestRequirement(requirements, []);
        if (typeof requirement === 'undefined') return; //We will be awarded this achiev soon.
        this.nextBuy = requirement.nextBuy;
        this.add = true; //This should be considered to buy.
        if (typeof AchievementRequired !== 'undefined') {
            this.requirements = [AchievementRequired].concat(AchievementRequired.requirements);
        }
        var cpsIncrease = 0;
        var price = 0;
        requirements.forEach(function(required, Building, map){
            const requiredDelta = required - Building.amount;
            price += Building.getSumPrice(requiredDelta);
            cpsIncrease += getBuildingCPSIncrease(Building, requiredDelta)
        });
        cpsIncrease += getTotalAchievementsToTarget(this) * calculateAchievementCpsIncrease();
        this.payback = calculatePayback(price, cpsIncrease);
        this.price = this.nextBuy.getPrice();
    }
}

/**
 * Gets the number of achievements we get by getting this achievement.
 * @param {Achievement} Achievement Which achievement are we looking.
 * @returns {number} number of required achievements.
 */
function getTotalAchievementsToTarget(Achievement) {
    if (typeof Achievement === 'undefined') return 0; //There is no achievement
    if (typeof Achievement.requirements === 'undefined') return 1; //This achievement does not unlock any other achievements
    return Achievement.requirements.length + 1;
}

/**
 * Generates a map of requirements that maps Game objects to their required amount.
 * @returns {Map.<Game.Object, number>}
 */
function generateRequirements() {
    var requirements = new Map();
    if (arguments.length == 1) { //generate requirements that require all buildings.
        for (var buildingName in Game.Objects) {
            requirements.set(Game.Objects[buildingName], arguments[0]);
        }
        return requirements;
    }
    if (arguments.length % 2 !== 0) throw "Invalid number of arguments";
    for (var i = 0; i < arguments.length; i += 2) {
        requirements.set(arguments[i], arguments[i + 1]);
    }
    return requirements;
}

/**
 * @return {Array.<Building>} List of all buildings
 */
function getBuildings() {
    var buildings = [];
    for (var buildingName in Game.Objects) {
        buildings.push(new Building(Game.Objects[buildingName]));
    }
    return buildings;
}

/**
 * @return {Array.<Upgrade>} List of all upgrades
 */
function getUpgrades() {
//Generates a list with all possible upgrades
    Upgrades = []; //Reset global upgrades list.
    var upgrades = [];
    { //payback functions
        { //CURSOR, 
            upgrades.push(new Upgrade(Game.Upgrades["Reinforced index finger"], CURSOR, generateRequirements(CURSOR, 1), undefined, Game.Achievements["Click"]));
            upgrades.push(new Upgrade(Game.Upgrades["Carpal tunnel prevention cream"], CURSOR, generateRequirements(CURSOR, 1), undefined, Game.Achievements["Click"]));
            upgrades.push(new Upgrade(Game.Upgrades["Ambidextrous"], CURSOR, generateRequirements(CURSOR, 10)));
            upgrades.push(new Upgrade(Game.Upgrades["Thousand fingers"], CURSOR, generateRequirements(CURSOR, 20)));
            upgrades.push(new Upgrade(Game.Upgrades["Million fingers"], CURSOR, generateRequirements(CURSOR, 40)));
            upgrades.push(new Upgrade(Game.Upgrades["Billion fingers"], CURSOR, generateRequirements(CURSOR, 80)));
            upgrades.push(new Upgrade(Game.Upgrades["Trillion fingers"], CURSOR, generateRequirements(CURSOR, 120)));
            upgrades.push(new Upgrade(Game.Upgrades["Quadrillion fingers"], CURSOR, generateRequirements(CURSOR, 160)));
            upgrades.push(new Upgrade(Game.Upgrades["Quintillion fingers"], CURSOR, generateRequirements(CURSOR, 200)));
            upgrades.push(new Upgrade(Game.Upgrades["Sextillion fingers"], CURSOR, generateRequirements(CURSOR, 240)));
            upgrades.push(new Upgrade(Game.Upgrades["Septillion fingers"], CURSOR, generateRequirements(CURSOR, 280)));
            upgrades.push(new Upgrade(Game.Upgrades["Octillion fingers"], CURSOR, generateRequirements(CURSOR, 320)));
        }
        { //Grandma
            upgrades.push(new Upgrade(Game.Upgrades["Forwards from grandma"], GRANDMA, generateRequirements(GRANDMA, 1), undefined, Game.Achievements["Grandma\'s cookies"]));
            upgrades.push(new Upgrade(Game.Upgrades["Steel-plated rolling pins"], GRANDMA, generateRequirements(GRANDMA, 5)));
            upgrades.push(new Upgrade(Game.Upgrades["Lubricated dentures"], GRANDMA, generateRequirements(GRANDMA, 25)));
            upgrades.push(new Upgrade(Game.Upgrades["Prune juice"], GRANDMA, generateRequirements(GRANDMA, 50), undefined, Game.Achievements["Sloppy kisses"]));
            upgrades.push(new Upgrade(Game.Upgrades["Double-thick glasses"], GRANDMA, generateRequirements(GRANDMA, 100), undefined, Game.Achievements["Retirement home"]));
            upgrades.push(new Upgrade(Game.Upgrades["Aging agents"], GRANDMA, generateRequirements(GRANDMA, 150), undefined, Game.Achievements["Friend of the ancients"]));
            upgrades.push(new Upgrade(Game.Upgrades["Xtreme walkers"], GRANDMA, generateRequirements(GRANDMA, 200), undefined, Game.Achievements["Ruler of the ancients"]));
            upgrades.push(new Upgrade(Game.Upgrades["The Unbridling"], GRANDMA, generateRequirements(GRANDMA, 250), undefined, Game.Achievements["The old never bothered me anyway"]));
            upgrades.push(new Upgrade(Game.Upgrades["Farmer grandmas"], GRANDMA, generateRequirements(FARM, 15, GRANDMA, 1)));
            upgrades.push(new Upgrade(Game.Upgrades["Miner grandmas"], GRANDMA, generateRequirements(MINE, 15, GRANDMA, 1)));
            upgrades.push(new Upgrade(Game.Upgrades["Worker grandmas"], GRANDMA, generateRequirements(FACTORY, 15, GRANDMA, 1)));
            upgrades.push(new Upgrade(Game.Upgrades["Banker grandmas"], GRANDMA, generateRequirements(BANK, 15, GRANDMA, 1)));
            upgrades.push(new Upgrade(Game.Upgrades["Priestess grandmas"], GRANDMA, generateRequirements(TEMPLE, 15, GRANDMA, 1)));
            upgrades.push(new Upgrade(Game.Upgrades["Witch grandmas"], GRANDMA, generateRequirements(WIZARD_TOWER, 15, GRANDMA, 1)));
            upgrades.push(new Upgrade(Game.Upgrades["Cosmic grandmas"], GRANDMA, generateRequirements(SHIPMENT, 15, GRANDMA, 1)));
            upgrades.push(new Upgrade(Game.Upgrades["Transmuted grandmas"], GRANDMA, generateRequirements(ALCHEMY_LAB, 15, GRANDMA, 1)));
            upgrades.push(new Upgrade(Game.Upgrades["Altered grandmas"], GRANDMA, generateRequirements(PORTAL, 15, GRANDMA, 1)));
            upgrades.push(new Upgrade(Game.Upgrades["Grandmas\' grandmas"], GRANDMA, generateRequirements(TIME_MACHINE, 15, GRANDMA, 1)));
            upgrades.push(new Upgrade(Game.Upgrades["Antigrandmas"], GRANDMA, generateRequirements(ANTIMATTER_CONDENSER, 15, GRANDMA, 1)));
            upgrades.push(new Upgrade(Game.Upgrades["Rainbow grandmas"], GRANDMA, generateRequirements(PRISM, 15, GRANDMA, 1)));
        }
        { //Farm
            upgrades.push(new Upgrade(Game.Upgrades["Cheap hoes"], FARM, generateRequirements(FARM, 1), undefined, Game.Achievements["My first farm"]));
            upgrades.push(new Upgrade(Game.Upgrades["Fertilizer"], FARM, generateRequirements(FARM, 5)));
            upgrades.push(new Upgrade(Game.Upgrades["Cookie trees"], FARM, generateRequirements(FARM, 25)));
            upgrades.push(new Upgrade(Game.Upgrades["Genetically-modified cookies"], FARM, generateRequirements(FARM, 50), undefined, Game.Achievements["Reap what you sow"]));
            upgrades.push(new Upgrade(Game.Upgrades["Gingerbread scarecrows"], FARM, generateRequirements(FARM, 100), undefined, Game.Achievements["Farm ill"]));
            upgrades.push(new Upgrade(Game.Upgrades["Pulsar sprinklers"], FARM, generateRequirements(FARM, 150), undefined, Game.Achievements["Perfected agriculture"]));
            upgrades.push(new Upgrade(Game.Upgrades["Fudge fungus"], FARM, generateRequirements(FARM, 200), undefined, Game.Achievements["Homegrown"]));
            upgrades.push(new Upgrade(Game.Upgrades["Wheat triffids"], FARM, generateRequirements(FARM, 250), undefined, Game.Achievements["Gardner extrodinaire"]));
        }
        { //Mine
            upgrades.push(new Upgrade(Game.Upgrades["Sugar gas"], MINE, generateRequirements(MINE, 1), undefined, Game.Achievements["You know the drill"]));
            upgrades.push(new Upgrade(Game.Upgrades["Megadrill"], MINE, generateRequirements(MINE, 5)));
            upgrades.push(new Upgrade(Game.Upgrades["Ultradrill"], MINE, generateRequirements(MINE, 25)));
            upgrades.push(new Upgrade(Game.Upgrades["Ultimadrill"], MINE, generateRequirements(MINE, 50), undefined, Game.Achievements["Excavation site"]));
            upgrades.push(new Upgrade(Game.Upgrades["H-bomb mining"], MINE, generateRequirements(MINE, 100), undefined, Game.Achievements["Hollow the planet"]));
            upgrades.push(new Upgrade(Game.Upgrades["Coreforge"], MINE, generateRequirements(MINE, 150), undefined, Game.Achievements["Can you dig it"]));
            upgrades.push(new Upgrade(Game.Upgrades["Planetsplitters"], MINE, generateRequirements(MINE, 200), undefined, Game.Achievements["The center of the Earth"]));
            upgrades.push(new Upgrade(Game.Upgrades["Canola oil wells"], MINE, generateRequirements(MINE, 250), undefined, Game.Achievements["Tectonic Ambassador"]));
        }
        { //Factory
            upgrades.push(new Upgrade(Game.Upgrades["Sturdier conveyor belts"], FACTORY, generateRequirements(FACTORY, 1), undefined, Game.Achievements["Production chain"]));
            upgrades.push(new Upgrade(Game.Upgrades["Child labor"], FACTORY, generateRequirements(FACTORY, 5)));
            upgrades.push(new Upgrade(Game.Upgrades["Sweatshop"], FACTORY, generateRequirements(FACTORY, 25)));
            upgrades.push(new Upgrade(Game.Upgrades["Radium reactors"], FACTORY, generateRequirements(FACTORY, 50), undefined, Game.Achievements["Industrial revolution"]));
            upgrades.push(new Upgrade(Game.Upgrades["Recombobulators"], FACTORY, generateRequirements(FACTORY, 100), undefined, Game.Achievements["Global warming"]));
            upgrades.push(new Upgrade(Game.Upgrades["Deep-bake process"], FACTORY, generateRequirements(FACTORY, 150), undefined, Game.Achievements["Ultimate automation"]));
            upgrades.push(new Upgrade(Game.Upgrades["Cyborg workforce"], FACTORY, generateRequirements(FACTORY, 200), undefined, Game.Achievements["Technocracy"]));
            upgrades.push(new Upgrade(Game.Upgrades["78-hour days"], FACTORY, generateRequirements(FACTORY, 250), undefined, Game.Achievements["Rise of the machines"]));
        }
        { //Bank
            upgrades.push(new Upgrade(Game.Upgrades["Taller tellers"], BANK, generateRequirements(BANK, 1), undefined, Game.Achievements["Pretty penny"]));
            upgrades.push(new Upgrade(Game.Upgrades["Scissor-resistant credit cards"], BANK, generateRequirements(BANK, 5)));
            upgrades.push(new Upgrade(Game.Upgrades["Acid-proof vaults"], BANK, generateRequirements(BANK, 25)));
            upgrades.push(new Upgrade(Game.Upgrades["Chocolate coins"], BANK, generateRequirements(BANK, 50), undefined, Game.Achievements["Fit the bill"]));
            upgrades.push(new Upgrade(Game.Upgrades["Exponential interest rates"], BANK, generateRequirements(BANK, 100), undefined, Game.Achievements["A loan in the dark"]));
            upgrades.push(new Upgrade(Game.Upgrades["Financial zen"], BANK, generateRequirements(BANK, 150), undefined, Game.Achievements["Need for greed"]));
            upgrades.push(new Upgrade(Game.Upgrades["Way of the wallet"], BANK, generateRequirements(BANK, 200), undefined, Game.Achievements["It\'s the economy, stupid"]));
            upgrades.push(new Upgrade(Game.Upgrades["The stuff rationale"], BANK, generateRequirements(BANK, 250), undefined, Game.Achievements["Acquire currency"]));
        }
        { //Temple
            upgrades.push(new Upgrade(Game.Upgrades["Golden idols"], TEMPLE, generateRequirements(TEMPLE, 1), undefined, Game.Achievements["Your time to shrine"]));
            upgrades.push(new Upgrade(Game.Upgrades["Sacrifices"], TEMPLE, generateRequirements(TEMPLE, 5)));
            upgrades.push(new Upgrade(Game.Upgrades["Delicious blessing"], TEMPLE, generateRequirements(TEMPLE, 25)));
            upgrades.push(new Upgrade(Game.Upgrades["Sun festival"], TEMPLE, generateRequirements(TEMPLE, 50), undefined, Game.Achievements["New-age cult"]));
            upgrades.push(new Upgrade(Game.Upgrades["Enlarged pantheon"], TEMPLE, generateRequirements(TEMPLE, 100), undefined, Game.Achievements["New-age cult"]));
            upgrades.push(new Upgrade(Game.Upgrades["Great Baker in the sky"], TEMPLE, generateRequirements(TEMPLE, 150), undefined, Game.Achievements["Organized religion"]));
            upgrades.push(new Upgrade(Game.Upgrades["Creation myth"], TEMPLE, generateRequirements(TEMPLE, 200), undefined, Game.Achievements["Fanaticism"]));
            upgrades.push(new Upgrade(Game.Upgrades["Theocracy"], TEMPLE, generateRequirements(TEMPLE, 250), undefined, Game.Achievements["Zealotry"]));
        }
        { //Wizard tower
            upgrades.push(new Upgrade(Game.Upgrades["Pointier hats"], WIZARD_TOWER, generateRequirements(WIZARD_TOWER, 1), undefined, Game.Achievements["Bewitched"]));
            upgrades.push(new Upgrade(Game.Upgrades["Beardlier beards"], WIZARD_TOWER, generateRequirements(WIZARD_TOWER, 5)));
            upgrades.push(new Upgrade(Game.Upgrades["Ancient grimoires"], WIZARD_TOWER, generateRequirements(WIZARD_TOWER, 25)));
            upgrades.push(new Upgrade(Game.Upgrades["Kitchen curses"], WIZARD_TOWER, generateRequirements(WIZARD_TOWER, 50), undefined, Game.Achievements["The sorcerer\'s apprentice"]));
            upgrades.push(new Upgrade(Game.Upgrades["School of sorcery"], WIZARD_TOWER, generateRequirements(WIZARD_TOWER, 100), undefined, Game.Achievements["Charms and enchantments"]));
            upgrades.push(new Upgrade(Game.Upgrades["Dark formulas"], WIZARD_TOWER, generateRequirements(WIZARD_TOWER, 150), undefined, Game.Achievements["Curses and maledictions"]));
            upgrades.push(new Upgrade(Game.Upgrades["Cookiemancy"], WIZARD_TOWER, generateRequirements(WIZARD_TOWER, 200), undefined, Game.Achievements["Magic kingdom"]));
            upgrades.push(new Upgrade(Game.Upgrades["Rabbit trick"], WIZARD_TOWER, generateRequirements(WIZARD_TOWER, 250), undefined, Game.Achievements["The wizarding world"]));
        }
        { //Shipment
            upgrades.push(new Upgrade(Game.Upgrades["Vanilla nebulae"], SHIPMENT, generateRequirements(SHIPMENT, 1), undefined, Game.Achievements["Expedition"]));
            upgrades.push(new Upgrade(Game.Upgrades["Wormholes"], SHIPMENT, generateRequirements(SHIPMENT, 5)));
            upgrades.push(new Upgrade(Game.Upgrades["Frequent flyer"], SHIPMENT, generateRequirements(SHIPMENT, 25)));
            upgrades.push(new Upgrade(Game.Upgrades["Warp drive"], SHIPMENT, generateRequirements(SHIPMENT, 50), undefined, Game.Achievements["Galactic highway"]));
            upgrades.push(new Upgrade(Game.Upgrades["Chocolate monoliths"], SHIPMENT, generateRequirements(SHIPMENT, 100), undefined, Game.Achievements["Far far away"]));
            upgrades.push(new Upgrade(Game.Upgrades["Generation ship"], SHIPMENT, generateRequirements(SHIPMENT, 150), undefined, Game.Achievements["Type II civilization"]));
            upgrades.push(new Upgrade(Game.Upgrades["Dyson sphere"], SHIPMENT, generateRequirements(SHIPMENT, 200), undefined, Game.Achievements["We come in peace"]));
            upgrades.push(new Upgrade(Game.Upgrades["The final frontier"], SHIPMENT, generateRequirements(SHIPMENT, 250), undefined, Game.Achievements["Parsec-masher"]));
        }
        { //Alchemy lab
            upgrades.push(new Upgrade(Game.Upgrades["Antimony"], ALCHEMY_LAB, generateRequirements(ALCHEMY_LAB, 1), undefined, Game.Achievements["Transmutation"]));
            upgrades.push(new Upgrade(Game.Upgrades["Essence of dough"], ALCHEMY_LAB, generateRequirements(ALCHEMY_LAB, 5)));
            upgrades.push(new Upgrade(Game.Upgrades["True chocolate"], ALCHEMY_LAB, generateRequirements(ALCHEMY_LAB, 25)));
            upgrades.push(new Upgrade(Game.Upgrades["Ambrosia"], ALCHEMY_LAB, generateRequirements(ALCHEMY_LAB, 50), undefined, Game.Achievements["Transmogrification"]));
            upgrades.push(new Upgrade(Game.Upgrades["Aqua crustulae"], ALCHEMY_LAB, generateRequirements(ALCHEMY_LAB, 100), undefined, Game.Achievements["Gold member"]));
            upgrades.push(new Upgrade(Game.Upgrades["Origin crucible"], ALCHEMY_LAB, generateRequirements(ALCHEMY_LAB, 150), undefined, Game.Achievements["Gild wars"]));
            upgrades.push(new Upgrade(Game.Upgrades["Theory of atomic fluidity"], ALCHEMY_LAB, generateRequirements(ALCHEMY_LAB, 200), undefined, Game.Achievements["The secrets of the universe"]));
            upgrades.push(new Upgrade(Game.Upgrades["Beige goo"], ALCHEMY_LAB, generateRequirements(ALCHEMY_LAB, 250), undefined, Game.Achievements["The work of a lifetime"]));
        }
        { //Portal
            upgrades.push(new Upgrade(Game.Upgrades["Ancient tablet"], PORTAL, generateRequirements(PORTAL, 1), undefined, Game.Achievements["A whole new world"]));
            upgrades.push(new Upgrade(Game.Upgrades["Insane oatling workers"], PORTAL, generateRequirements(PORTAL, 5)));
            upgrades.push(new Upgrade(Game.Upgrades["Soul bond"], PORTAL, generateRequirements(PORTAL, 25)));
            upgrades.push(new Upgrade(Game.Upgrades["Sanity dance"], PORTAL, generateRequirements(PORTAL, 50), undefined, Game.Achievements["Now you\'re thinking"]));
            upgrades.push(new Upgrade(Game.Upgrades["Brane transplant"], PORTAL, generateRequirements(PORTAL, 100), undefined, Game.Achievements["Dimensional shift"]));
            upgrades.push(new Upgrade(Game.Upgrades["Deity-sized portals"], PORTAL, generateRequirements(PORTAL, 150), undefined, Game.Achievements["Brain-split"]));
            upgrades.push(new Upgrade(Game.Upgrades["End of times back-up plan"], PORTAL, generateRequirements(PORTAL, 200), undefined, Game.Achievements["Realm of the Mad God"]));
            upgrades.push(new Upgrade(Game.Upgrades["Maddening chants"], PORTAL, generateRequirements(PORTAL, 250), undefined, Game.Achievements["A place lost in time"]));
        }
        { //Time machine
            upgrades.push(new Upgrade(Game.Upgrades["Flux capacitors"], TIME_MACHINE, generateRequirements(TIME_MACHINE, 1), undefined, Game.Achievements["Time warp"]));
            upgrades.push(new Upgrade(Game.Upgrades["Time paradox resolver"], TIME_MACHINE, generateRequirements(TIME_MACHINE, 5)));
            upgrades.push(new Upgrade(Game.Upgrades["Quantum conundrum"], TIME_MACHINE, generateRequirements(TIME_MACHINE, 25)));
            upgrades.push(new Upgrade(Game.Upgrades["Causality enforcer"], TIME_MACHINE, generateRequirements(TIME_MACHINE, 50), undefined, Game.Achievements["Alternate timeline"]));
            upgrades.push(new Upgrade(Game.Upgrades["Yestermorrow comparators"], TIME_MACHINE, generateRequirements(TIME_MACHINE, 100), undefined, Game.Achievements["Rewriting history"]));
            upgrades.push(new Upgrade(Game.Upgrades["Far future enactment"], TIME_MACHINE, generateRequirements(TIME_MACHINE, 150), undefined, Game.Achievements["Time duke"]));
            upgrades.push(new Upgrade(Game.Upgrades["Great loop hypothesis"], TIME_MACHINE, generateRequirements(TIME_MACHINE, 200), undefined, Game.Achievements["Forever and ever"]));
            upgrades.push(new Upgrade(Game.Upgrades["Cookietopian moments of maybe"], TIME_MACHINE, generateRequirements(TIME_MACHINE, 250), undefined, Game.Achievements["Heat death"]));
        }
        { //Antimatter condensers
            upgrades.push(new Upgrade(Game.Upgrades["Sugar bosons"], ANTIMATTER_CONDENSER, generateRequirements(ANTIMATTER_CONDENSER, 1), undefined, Game.Achievements["Antibatter"]));
            upgrades.push(new Upgrade(Game.Upgrades["String theory"], ANTIMATTER_CONDENSER, generateRequirements(ANTIMATTER_CONDENSER, 5)));
            upgrades.push(new Upgrade(Game.Upgrades["Large macaron collider"], ANTIMATTER_CONDENSER, generateRequirements(ANTIMATTER_CONDENSER, 25)));
            upgrades.push(new Upgrade(Game.Upgrades["Big bang bake"], ANTIMATTER_CONDENSER, generateRequirements(ANTIMATTER_CONDENSER, 50), undefined, Game.Achievements["Quirky quarks"]));
            upgrades.push(new Upgrade(Game.Upgrades["Reverse cyclotrons"], ANTIMATTER_CONDENSER, generateRequirements(ANTIMATTER_CONDENSER, 100), undefined, Game.Achievements["It does matter!"]));
            upgrades.push(new Upgrade(Game.Upgrades["Nanocosmics"], ANTIMATTER_CONDENSER, generateRequirements(ANTIMATTER_CONDENSER, 150), undefined, Game.Achievements["Molecular maestro"]));
            upgrades.push(new Upgrade(Game.Upgrades["The Pulse"], ANTIMATTER_CONDENSER, generateRequirements(ANTIMATTER_CONDENSER, 200), undefined, Game.Achievements["Walk the planck"]));
            upgrades.push(new Upgrade(Game.Upgrades["Some other super-tiny fundamental particle? Probably?"], ANTIMATTER_CONDENSER, generateRequirements(ANTIMATTER_CONDENSER, 250), undefined, Game.Achievements["Microcosm"]));
        }
        { //Prisms
            upgrades.push(new Upgrade(Game.Upgrades["Gem polish"], PRISM, generateRequirements(PRISM, 1), undefined, Game.Achievements["Lone photon"]));
            upgrades.push(new Upgrade(Game.Upgrades["9th color"], PRISM, generateRequirements(PRISM, 5)));
            upgrades.push(new Upgrade(Game.Upgrades["Chocolate light"], PRISM, generateRequirements(PRISM, 25)));
            upgrades.push(new Upgrade(Game.Upgrades["Grainbow"], PRISM, generateRequirements(PRISM, 50), undefined, Game.Achievements["Dazzling glimmer"]));
            upgrades.push(new Upgrade(Game.Upgrades["Pure cosmic light"], PRISM, generateRequirements(PRISM, 100), undefined, Game.Achievements["Blinding flash"]));
            upgrades.push(new Upgrade(Game.Upgrades["Glow-in-the-dark"], PRISM, generateRequirements(PRISM, 150), undefined, Game.Achievements["Unending glow"]));
            upgrades.push(new Upgrade(Game.Upgrades["Lux sanctorum"], PRISM, generateRequirements(PRISM, 200), undefined, Game.Achievements["Rise and shine"]));
            upgrades.push(new Upgrade(Game.Upgrades["Reverse shadows"], PRISM, generateRequirements(PRISM, 250), undefined, Game.Achievements["Bright future"]));
        }
        { //Bingo Center
            if (Game.HasAchiev("Elder") && Game.researchT <= 0) {
                upgrades.push(new Upgrade(Game.Upgrades["Bingo center/Research facility"], GRANDMA, undefined, undefined, undefined, 4));
                upgrades.push(new Upgrade(Game.Upgrades["Specialized chocolate chips"], undefined, undefined, [Upgrades["Bingo center/Research facility"]], undefined, undefined, 1));
                upgrades.push(new Upgrade(Game.Upgrades["Designer cocoa beans"], undefined, undefined, [Upgrades["Specialized chocolate chips"]], undefined, undefined, 2));
                upgrades.push(new Upgrade(Game.Upgrades["Ritual rolling pins"], GRANDMA, undefined, [Upgrades["Designer cocoa beans"]]));
                upgrades.push(new Upgrade(Game.Upgrades["Underworld ovens"], undefined, undefined, [Upgrades["Ritual rolling pins"]], undefined, undefined, 3));
                upgrades.push(new Upgrade(Game.Upgrades["One mind"], undefined, undefined, [Upgrades["Underworld ovens"]]));
                upgrades.push(new Upgrade(Game.Upgrades["Exotic nuts"], undefined, undefined, [Upgrades["One mind"]], undefined, undefined, 4));
                upgrades.push(new Upgrade(Game.Upgrades["Communal brainsweep"], undefined, undefined, [Upgrades["Exotic nuts"]]));
                upgrades.push(new Upgrade(Game.Upgrades["Arcane sugar"], undefined, undefined, [Upgrades["Communal brainsweep"]], undefined, undefined, 5));
                upgrades.push(new Upgrade(Game.Upgrades["Elder Pact"], undefined, undefined, [Upgrades["Arcane sugar"]]));
            }
        }
        { //Cookies
            upgrades.push(new Upgrade(Game.Upgrades["Plain cookies"]));
            upgrades.push(new Upgrade(Game.Upgrades["Sugar cookies"]));
            upgrades.push(new Upgrade(Game.Upgrades["Oatmeal raisin cookies"]));
            upgrades.push(new Upgrade(Game.Upgrades["Peanut butter cookies"]));
            upgrades.push(new Upgrade(Game.Upgrades["Coconut cookies"]));
            upgrades.push(new Upgrade(Game.Upgrades["White chocolate cookies"]));
            upgrades.push(new Upgrade(Game.Upgrades["Macadamia nut cookies"]));
            upgrades.push(new Upgrade(Game.Upgrades["Double-chip cookies"]));
            upgrades.push(new Upgrade(Game.Upgrades["White chocolate macadamia nut cookies"]));
            upgrades.push(new Upgrade(Game.Upgrades["All-chocolate cookies"]));
            upgrades.push(new Upgrade(Game.Upgrades["Dark chocolate-coated cookies"]));
            upgrades.push(new Upgrade(Game.Upgrades["White chocolate-coated cookies"]));
            upgrades.push(new Upgrade(Game.Upgrades["Eclipse cookies"]));
            upgrades.push(new Upgrade(Game.Upgrades["Zebra cookies"]));
            upgrades.push(new Upgrade(Game.Upgrades["Snickerdoodles"]));
            upgrades.push(new Upgrade(Game.Upgrades["Stroopwafels"]));
            upgrades.push(new Upgrade(Game.Upgrades["Macaroons"]));
            upgrades.push(new Upgrade(Game.Upgrades["Madeleines"]));
            upgrades.push(new Upgrade(Game.Upgrades["Palmiers"]));
            upgrades.push(new Upgrade(Game.Upgrades["Palets"]));
            upgrades.push(new Upgrade(Game.Upgrades["Sabl&eacute;s"]));
            if (Game.Has("Box of brand biscuits")) {
                upgrades.push(new Upgrade(Game.Upgrades["Caramoas"]));
                upgrades.push(new Upgrade(Game.Upgrades["Sagalongs"]));
                upgrades.push(new Upgrade(Game.Upgrades["Shortfoils"]));
                upgrades.push(new Upgrade(Game.Upgrades["Win mints"]));
            }
            upgrades.push(new Upgrade(Game.Upgrades["Gingerbread men"]));
            upgrades.push(new Upgrade(Game.Upgrades["Gingerbread trees"]));
            upgrades.push(new Upgrade(Game.Upgrades["Pure black chocolate cookies"]));
            upgrades.push(new Upgrade(Game.Upgrades["Pure white chocolate cookies"]));
            upgrades.push(new Upgrade(Game.Upgrades["Ladyfingers"]));
            upgrades.push(new Upgrade(Game.Upgrades["Tuiles"]));
            upgrades.push(new Upgrade(Game.Upgrades["Chocolate-stuffed biscuits"]));
            upgrades.push(new Upgrade(Game.Upgrades["Checker cookies"]));
            upgrades.push(new Upgrade(Game.Upgrades["Butter cookies"]));
            upgrades.push(new Upgrade(Game.Upgrades["Cream cookies"]));
            upgrades.push(new Upgrade(Game.Upgrades["Gingersnaps"]));
            upgrades.push(new Upgrade(Game.Upgrades["Cinnamon cookies"]));
            upgrades.push(new Upgrade(Game.Upgrades["Vanity cookies"]));
            upgrades.push(new Upgrade(Game.Upgrades["Cigars"]));
            upgrades.push(new Upgrade(Game.Upgrades["Pinwheel cookies"]));
            upgrades.push(new Upgrade(Game.Upgrades["Fudge squares"]));
            upgrades.push(new Upgrade(Game.Upgrades["Shortbread biscuits"]));
            upgrades.push(new Upgrade(Game.Upgrades["Millionaires' shortbreads"]));
            upgrades.push(new Upgrade(Game.Upgrades["Caramel cookies"]));
            upgrades.push(new Upgrade(Game.Upgrades["Empire biscuits"]));
            if (Game.Has("Tin of british tea biscuits")) {
                upgrades.push(new Upgrade(Game.Upgrades["British tea biscuits"]));
                upgrades.push(new Upgrade(Game.Upgrades["Chocolate british tea biscuits"]), undefined, undefined, [Upgrades["British tea biscuits"]]);
                upgrades.push(new Upgrade(Game.Upgrades["Round british tea biscuits"]), undefined, undefined, [Upgrades["Chocolate british tea biscuits"]]);
                upgrades.push(new Upgrade(Game.Upgrades["Round chocolate british tea biscuits"]), undefined, undefined, [Upgrades["Round british tea biscuits"]]);
                upgrades.push(new Upgrade(Game.Upgrades["Round british tea biscuits with heart motif"]), undefined, undefined, [Upgrades["Round chocolate british tea biscuits"]]);
                upgrades.push(new Upgrade(Game.Upgrades["Round chocolate british tea biscuits with heart motif"]), undefined, undefined, [Upgrades["Round british tea biscuits with heart motif"]]);
            }
            if (Game.Has("Box of brand biscuits")) {
                upgrades.push(new Upgrade(Game.Upgrades["Fig gluttons"]));
                upgrades.push(new Upgrade(Game.Upgrades["Loreols"]));
                upgrades.push(new Upgrade(Game.Upgrades["Jaffa cakes"]));
                upgrades.push(new Upgrade(Game.Upgrades["Grease's cups"]));
                upgrades.push(new Upgrade(Game.Upgrades["Digits"]));
            }
            if (Game.Has("Tin of butter cookies")) {
                upgrades.push(new Upgrade(Game.Upgrades["Butter horseshoes"]));
                upgrades.push(new Upgrade(Game.Upgrades["Butter pucks"]));
                upgrades.push(new Upgrade(Game.Upgrades["Butter knots"]));
                upgrades.push(new Upgrade(Game.Upgrades["Butter slabs"]));
                upgrades.push(new Upgrade(Game.Upgrades["Butter swirls"]));
            }
            if (Game.Has("Box of macarons")) {
                upgrades.push(new Upgrade(Game.Upgrades["Rose macarons"]));
                upgrades.push(new Upgrade(Game.Upgrades["Lemon macarons"]));
                upgrades.push(new Upgrade(Game.Upgrades["Chocolate macarons"]));
                upgrades.push(new Upgrade(Game.Upgrades["Pistachio macarons"]));
                upgrades.push(new Upgrade(Game.Upgrades["Hazelnut macarons"]));
                upgrades.push(new Upgrade(Game.Upgrades["Violet macarons"]));
                upgrades.push(new Upgrade(Game.Upgrades["Caramel macarons"]));
                upgrades.push(new Upgrade(Game.Upgrades["Licorice macarons"]));
            }
        }
        {
            upgrades.push(new Upgrade(Game.Upgrades["Milk chocolate butter biscuit"], undefined, generateRequirements(100)));
            upgrades.push(new Upgrade(Game.Upgrades["Dark chocolate butter biscuit"], undefined, generateRequirements(150)));
            upgrades.push(new Upgrade(Game.Upgrades["White chocolate butter biscuit"], undefined, generateRequirements(200)));
            upgrades.push(new Upgrade(Game.Upgrades["Ruby chocolate butter biscuit"], undefined, generateRequirements(250)));
        }
        { //Kitten
            if (Game.milkProgress >= 0.5) {
                upgrades.push(new Upgrade(Game.Upgrades["Kitten helpers"]));
                if (Game.milkProgress >= 1) {
                    upgrades.push(new Upgrade(Game.Upgrades["Kitten workers"]));
                    if (Game.milkProgress >= 2) {
                        upgrades.push(new Upgrade(Game.Upgrades["Kitten engineers"]));
                        if (Game.milkProgress >= 3) {
                            upgrades.push(new Upgrade(Game.Upgrades["Kitten overseers"]));
                            if (Game.milkProgress >= 4) {
                                upgrades.push(new Upgrade(Game.Upgrades["Kitten managers"]));
                                if (Game.milkProgress >= 5) {
                                    upgrades.push(new Upgrade(Game.Upgrades["Kitten accountants"]));
                                    if (Game.milkProgress >= 6) {
                                        upgrades.push(new Upgrade(Game.Upgrades["Kitten specialists"]));
                                        if (Game.milkProgress >= 7)
                                            upgrades.push(new Upgrade(Game.Upgrades["Kitten experts"]));
                                            
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        { //Heavenly Chips Power
            if (Game.prestige > 0 ){
                upgrades.push(new Upgrade(Game.Upgrades["Heavenly chip secret"]));
                if (Game.Has("Heavenly chip secret")) {
                    upgrades.push(new Upgrade(Game.Upgrades["Heavenly cookie stand"]));
                    if (Game.Has("Heavenly cookie stand")) {
                        upgrades.push(new Upgrade(Game.Upgrades["Heavenly bakery"]));
                        if (Game.Has("Heavenly bakery")) {
                            upgrades.push(new Upgrade(Game.Upgrades["Heavenly confectionery"]));
                            if (Game.Has("Heavenly confectionery"))
                                upgrades.push(new Upgrade(Game.Upgrades["Heavenly key"]));
                        }
                    }
                }
            }
        }
        { //Mouse
            if (Game.HasUnlocked("Plastic mouse"))
                upgrades.push(new Upgrade(Game.Upgrades["Plastic mouse"]));
            if (Game.HasUnlocked("Iron mouse"))
                upgrades.push(new Upgrade(Game.Upgrades["Iron mouse"]));
            if (Game.HasUnlocked("Titanium mouse"))
                upgrades.push(new Upgrade(Game.Upgrades["Titanium mouse"]));
            if (Game.HasUnlocked("Adamantium mouse"))
                upgrades.push(new Upgrade(Game.Upgrades["Adamantium mouse"]));
            if (Game.HasUnlocked("Unobtainium mouse"))
                upgrades.push(new Upgrade(Game.Upgrades["Unobtainium mouse"]));
            if (Game.HasUnlocked("Eludium mouse"))
                upgrades.push(new Upgrade(Game.Upgrades["Eludium mouse"]));
            if (Game.HasUnlocked("Wishalloy mouse"))
                upgrades.push(new Upgrade(Game.Upgrades["Wishalloy mouse"]));
            if (Game.HasUnlocked("Fantasteel mouse"))
                upgrades.push(new Upgrade(Game.Upgrades["Fantasteel mouse"]));
            if (Game.HasUnlocked("Nevercrack mouse"))
                upgrades.push(new Upgrade(Game.Upgrades["Nevercrack mouse"]));
        }
    }
    return upgrades;
}

/**
 * @return {Array.<Achievement>} List of all achievements
 */
function getAchievements() {
    var achievement = [];
    { //Cursor
        achievement.push(new Achievement(Game.Achievements["Click"], generateRequirements(CURSOR, 1)));
        achievement.push(new Achievement(Game.Achievements["Double-click"], generateRequirements(CURSOR, 2)));
        achievement.push(new Achievement(Game.Achievements["Mouse wheel"], generateRequirements(CURSOR, 50)));
        achievement.push(new Achievement(Game.Achievements["Of Mice and Men"], generateRequirements(CURSOR, 100)));
        achievement.push(new Achievement(Game.Achievements["The Digital"], generateRequirements(CURSOR, 200)));
        achievement.push(new Achievement(Game.Achievements["Extreme polydactyly"], generateRequirements(CURSOR, 300)));
        achievement.push(new Achievement(Game.Achievements["Dr. T"], generateRequirements(CURSOR, 400)));
        achievement.push(new Achievement(Game.Achievements["Thumbs, phalanges, metacarpals"], generateRequirements(CURSOR, 500)));
    }
    { //Grandma
        achievement.push(new Achievement(Game.Achievements["Grandma\'s cookies"], generateRequirements(GRANDMA, 1)));
        achievement.push(new Achievement(Game.Achievements["Sloppy kisses"], generateRequirements(GRANDMA, 50)));
        achievement.push(new Achievement(Game.Achievements["Retirement home"], generateRequirements(GRANDMA, 100)));
        achievement.push(new Achievement(Game.Achievements["Friend of the ancients"], generateRequirements(GRANDMA, 150)));
        achievement.push(new Achievement(Game.Achievements["Ruler of the ancients"], generateRequirements(GRANDMA, 200)));
        achievement.push(new Achievement(Game.Achievements["The old never bothered me anyway"], generateRequirements(GRANDMA, 250)));
        achievement.push(new Achievement(Game.Achievements["The agemaster"], generateRequirements(GRANDMA, 300)));
        achievement.push(new Achievement(Game.Achievements["To oldly go"], generateRequirements(GRANDMA, 350)));
    }
    { //Farm
        achievement.push(new Achievement(Game.Achievements["My first farm"], generateRequirements(FARM, 1)));
        achievement.push(new Achievement(Game.Achievements["Reap what you sow"], generateRequirements(FARM, 50)));
        achievement.push(new Achievement(Game.Achievements["Farm ill"], generateRequirements(FARM, 100)));
        achievement.push(new Achievement(Game.Achievements["Perfected agriculture"], generateRequirements(FARM, 150)));
        achievement.push(new Achievement(Game.Achievements["Homegrown"], generateRequirements(FARM, 200)));
        achievement.push(new Achievement(Game.Achievements["Gardener extraordinaire"], generateRequirements(FARM, 250)));
        achievement.push(new Achievement(Game.Achievements["Seedy business"], generateRequirements(FARM, 300)));
    }
    { //Mine
        achievement.push(new Achievement(Game.Achievements["You know the drill"], generateRequirements(MINE, 1)));
        achievement.push(new Achievement(Game.Achievements["Excavation site"], generateRequirements(MINE, 50)));
        achievement.push(new Achievement(Game.Achievements["Hollow the planet"], generateRequirements(MINE, 100)));
        achievement.push(new Achievement(Game.Achievements["Can you dig it"], generateRequirements(MINE, 150)));
        achievement.push(new Achievement(Game.Achievements["The center of the Earth"], generateRequirements(MINE, 200)));
        achievement.push(new Achievement(Game.Achievements["Tectonic ambassador"], generateRequirements(MINE, 250)));
        achievement.push(new Achievement(Game.Achievements["Freak fracking"], generateRequirements(MINE, 300)));
    }
    { //Factory
        achievement.push(new Achievement(Game.Achievements["Production chain"], generateRequirements(FACTORY, 1)));
        achievement.push(new Achievement(Game.Achievements["Industrial revolution"], generateRequirements(FACTORY, 50)));
        achievement.push(new Achievement(Game.Achievements["Global warming"], generateRequirements(FACTORY, 100)));
        achievement.push(new Achievement(Game.Achievements["Ultimate automation"], generateRequirements(FACTORY, 150)));
        achievement.push(new Achievement(Game.Achievements["Technocracy"], generateRequirements(FACTORY, 200)));
        achievement.push(new Achievement(Game.Achievements["Rise of the machines"], generateRequirements(FACTORY, 250)));
        achievement.push(new Achievement(Game.Achievements["Modern times"], generateRequirements(FACTORY, 300)));
    }
    { //Bank
        achievement.push(new Achievement(Game.Achievements["Pretty penny"], generateRequirements(BANK, 1)));
        achievement.push(new Achievement(Game.Achievements["Fit the bill"], generateRequirements(BANK, 50)));
        achievement.push(new Achievement(Game.Achievements["A loan in the dark"], generateRequirements(BANK, 100)));
        achievement.push(new Achievement(Game.Achievements["Need for greed"], generateRequirements(BANK, 150)));
        achievement.push(new Achievement(Game.Achievements["It's the economy, stupid"], generateRequirements(BANK, 200)));
        achievement.push(new Achievement(Game.Achievements["Acquire currency"], generateRequirements(BANK, 250)));
        achievement.push(new Achievement(Game.Achievements["The nerve of war"], generateRequirements(BANK, 300)));
    }
    { //Temple
        achievement.push(new Achievement(Game.Achievements["Your time to shrine"], generateRequirements(TEMPLE, 1)));
        achievement.push(new Achievement(Game.Achievements["Shady sect"], generateRequirements(TEMPLE, 50)));
        achievement.push(new Achievement(Game.Achievements["New-age cult"], generateRequirements(TEMPLE, 100)));
        achievement.push(new Achievement(Game.Achievements["Organized religion"], generateRequirements(TEMPLE, 150)));
        achievement.push(new Achievement(Game.Achievements["Fanaticism"], generateRequirements(TEMPLE, 200)));
        achievement.push(new Achievement(Game.Achievements["Zealotry"], generateRequirements(TEMPLE, 250)));
        achievement.push(new Achievement(Game.Achievements["Wololo"], generateRequirements(TEMPLE, 300)));
    }
    { //Wizard Tower
        achievement.push(new Achievement(Game.Achievements["Bewitched"], generateRequirements(WIZARD_TOWER, 1)));
        achievement.push(new Achievement(Game.Achievements["The sorcerer's apprentice"], generateRequirements(WIZARD_TOWER, 50)));
        achievement.push(new Achievement(Game.Achievements["Charms and enchantments"], generateRequirements(WIZARD_TOWER, 100)));
        achievement.push(new Achievement(Game.Achievements["Curses and maledictions"], generateRequirements(WIZARD_TOWER, 150)));
        achievement.push(new Achievement(Game.Achievements["Magic kingdom"], generateRequirements(WIZARD_TOWER, 200)));
        achievement.push(new Achievement(Game.Achievements["The wizarding world"], generateRequirements(WIZARD_TOWER, 250)));
        achievement.push(new Achievement(Game.Achievements["And now for my next trick, I'll need a volunteer from the audience"], generateRequirements(WIZARD_TOWER, 300)));
    }
    { //Shipment
        achievement.push(new Achievement(Game.Achievements["Expedition"], generateRequirements(SHIPMENT, 1)));
        achievement.push(new Achievement(Game.Achievements["Galactic highway"], generateRequirements(SHIPMENT, 50)));
        achievement.push(new Achievement(Game.Achievements["Far far away"], generateRequirements(SHIPMENT, 100)));
        achievement.push(new Achievement(Game.Achievements["Type II civilization"], generateRequirements(SHIPMENT, 150)));
        achievement.push(new Achievement(Game.Achievements["We come in peace"], generateRequirements(SHIPMENT, 200)));
        achievement.push(new Achievement(Game.Achievements["Parsec-masher"], generateRequirements(SHIPMENT, 250)));
        achievement.push(new Achievement(Game.Achievements["It's not delivery"], generateRequirements(SHIPMENT, 300)));
    }
    { //Alchemy
        achievement.push(new Achievement(Game.Achievements["Transmutation"], generateRequirements(ALCHEMY_LAB, 1)));
        achievement.push(new Achievement(Game.Achievements["Transmogrification"], generateRequirements(ALCHEMY_LAB, 50)));
        achievement.push(new Achievement(Game.Achievements["Gold member"], generateRequirements(ALCHEMY_LAB, 100)));
        achievement.push(new Achievement(Game.Achievements["Gild wars"], generateRequirements(ALCHEMY_LAB, 150)));
        achievement.push(new Achievement(Game.Achievements["The secrets of the universe"], generateRequirements(ALCHEMY_LAB, 200)));
        achievement.push(new Achievement(Game.Achievements["The work of a lifetime"], generateRequirements(ALCHEMY_LAB, 250)));
        achievement.push(new Achievement(Game.Achievements["Gold, Jerry! Gold!"], generateRequirements(ALCHEMY_LAB, 300)));
    }
    { //Portals
        achievement.push(new Achievement(Game.Achievements["A whole new world"], generateRequirements(PORTAL, 1)));
        achievement.push(new Achievement(Game.Achievements["Now you\'re thinking"], generateRequirements(PORTAL, 50)));
        achievement.push(new Achievement(Game.Achievements["Dimensional shift"], generateRequirements(PORTAL, 100)));
        achievement.push(new Achievement(Game.Achievements["Brain-split"], generateRequirements(PORTAL, 150)));
        achievement.push(new Achievement(Game.Achievements["Realm of the Mad God"], generateRequirements(PORTAL, 200)));
        achievement.push(new Achievement(Game.Achievements["A place lost in time"], generateRequirements(PORTAL, 250)));
        achievement.push(new Achievement(Game.Achievements["Forbidden zone"], generateRequirements(PORTAL, 300)));
    }
    { //Time Machines
        achievement.push(new Achievement(Game.Achievements["Time warp"], generateRequirements(TIME_MACHINE, 1)));
        achievement.push(new Achievement(Game.Achievements["Alternate timeline"], generateRequirements(TIME_MACHINE, 50)));
        achievement.push(new Achievement(Game.Achievements["Rewriting history"], generateRequirements(TIME_MACHINE, 100)));
        achievement.push(new Achievement(Game.Achievements["Time duke"], generateRequirements(TIME_MACHINE, 150)));
        achievement.push(new Achievement(Game.Achievements["Forever and ever"], generateRequirements(TIME_MACHINE, 200)));
        achievement.push(new Achievement(Game.Achievements["Heat death"], generateRequirements(TIME_MACHINE, 250)));
        achievement.push(new Achievement(Game.Achievements["cookie clicker forever and forever a hundred years cookie clicker, all day long forever, forever a hundred times, over and over cookie clicker adventures dot com"], generateRequirements(TIME_MACHINE, 300)));//Y u do dis? No capital letter at start and huge ass achiev name!
    }
    { //Antimatter Condensers
        achievement.push(new Achievement(Game.Achievements["Antibatter"], generateRequirements(ANTIMATTER_CONDENSER, 1)));
        achievement.push(new Achievement(Game.Achievements["Quirky quarks"], generateRequirements(ANTIMATTER_CONDENSER, 50)));
        achievement.push(new Achievement(Game.Achievements["It does matter!"], generateRequirements(ANTIMATTER_CONDENSER, 100)));
        achievement.push(new Achievement(Game.Achievements["Molecular maestro"], generateRequirements(ANTIMATTER_CONDENSER, 150)));
        achievement.push(new Achievement(Game.Achievements["Walk the planck"], generateRequirements(ANTIMATTER_CONDENSER, 200)));
        achievement.push(new Achievement(Game.Achievements["Microcosm"], generateRequirements(ANTIMATTER_CONDENSER, 250)));
        achievement.push(new Achievement(Game.Achievements["Scientists baffled everywhere"], generateRequirements(ANTIMATTER_CONDENSER, 300)));
    }
    { //Prims
        achievement.push(new Achievement(Game.Achievements["Lone photon"], generateRequirements(PRISM, 1)));
        achievement.push(new Achievement(Game.Achievements["Dazzling glimmer"], generateRequirements(PRISM, 50)));
        achievement.push(new Achievement(Game.Achievements["Blinding flash"], generateRequirements(PRISM, 100)));
        achievement.push(new Achievement(Game.Achievements["Unending glow"], generateRequirements(PRISM, 150)));
        achievement.push(new Achievement(Game.Achievements["Rise and shine"], generateRequirements(PRISM, 200)));
        achievement.push(new Achievement(Game.Achievements["Bright future"], generateRequirements(PRISM, 250)));
        achievement.push(new Achievement(Game.Achievements["Harmony of the spheres"], generateRequirements(PRISM, 300)));
    }
    {//All buildings
        achievement.push(new Achievement(Game.Achievements["One with everything"], generateRequirements(1)));
        achievement.push(new Achievement(Game.Achievements["Centennial"], generateRequirements(100)));
        achievement.push(new Achievement(Game.Achievements["Centennial and a half"], generateRequirements(150)));
        achievement.push(new Achievement(Game.Achievements["Bicentennial"], generateRequirements(200)));
        achievement.push(new Achievement(Game.Achievements["Bicentennial and a half"], generateRequirements(250)));
        achievement.push(new Achievement(Game.Achievements["Mathematician"], generateRequirements(PRISM, 1, ANTIMATTER_CONDENSER, 2, TIME_MACHINE, 4, PORTAL, 8, ALCHEMY_LAB, 16, SHIPMENT, 32, WIZARD_TOWER, 64, TEMPLE, 128, BANK, 128, FACTORY, 128, MINE, 128, FARM, 128, GRANDMA, 128, CURSOR, 128)));
        achievement.push(new Achievement(Game.Achievements["Base 10"], generateRequirements(PRISM, 10, ANTIMATTER_CONDENSER, 20, TIME_MACHINE, 30, PORTAL, 40, ALCHEMY_LAB, 50, SHIPMENT, 606, WIZARD_TOWER, 70, TEMPLE, 80, BANK, 90, FACTORY, 100, MINE, 110, FARM, 120, GRANDMA, 130, CURSOR, 140)));
    }
    return achievement;
}


/**
 * Returns the best thing to buy
 * @param {Array} buyableList List of buyable objects
 * @return {Building} The best thing to buy
 */
function calculateBestBuy(buyableList) {
    buyableList.sort(function(a,b) {return (a.payback > b.payback) ? 1 : ((b.payback > a.payback) ? -1 : 0);} );
    return buyableList[0];
}


/**
 * Calculates the best thing to buy
 * @return {Building} The best thing to buy
 */
function getBestBuy() {
    var possibilities = {
        length: 0,
        push: function() {
            // possibilities.length is automatically incremented every time an element is added.
            for (var i in arguments) {
                var argument = arguments[i];
                if (argument.constructor === Array) {
                    argument.forEach(function (element) { //element is either Building, Upgrade or Achievement.
                        if (element.add) [].push.call(possibilities, element);
                    })
                }
            }
        },
        best: function() {
            var len = this.length;
            var min = Infinity;
            var minObject;
            while (len--) {
                if (this[len].payback <= min) {
                    minObject = this[len];
                    min = minObject.payback;
                }
            }
            return minObject;
        }
    };
    possibilities.push(getBuildings(), getUpgrades(), getAchievements());
    /*if (season_changer_unlocked)
     possibilities.push(getBestSeason());*/
    return possibilities.best();
}

/*******************************************************************************RESERVE******************************************************************************/
/**
 * Creates new instance of Reserve
 * @return {Reserve} The new instance
 */
function Reserve() {
    const MAX_RESERVE = 7;
    this.reserveAmount = 0;
    this.reserveLevel = 0;

    function goldenCookieMultiplier() {
        var mult = 1;
        if (this.wrath > 0 && Game.hasAura('Unholy Dominion')) mult *= 1.1;
        else if (this.wrath == 0 && Game.hasAura('Ancestral Metamorphosis')) mult *= 1.1;
        return mult;
    }

    this.lucky = function () { //This adds 15% of current cookies in bank or 15 mins of production
        //Reserve for this must make 15% of cookies in bank equal to 15 mins of production
        var cps = Game.frenzy > 0 ? Game.cookiesPs / Game.frenzyPower : Game.cookiesPs;
        if (Game.frenzy > 0) cps = Game.cookiesPs / Game.frenzyPower;
        return (cps * 15 * 60) / .15; //Multiplier doesn't matter here as it would cancel out.
    };

    function calculateCookieChainReserve(cps) {
        var digit = 7;
        if (Game.elderWrath === 3) digit = 6; //With last level of elder wrath cookie chainDigits digits must be 6
        var chainDigits = 1 + Math.max(0, Math.ceil(Math.log(Game.cookies) / Math.LN10) - 10); //Starting number of digits for new chain
        const goldenCookieMultiplier = goldenCookieMultiplier();
        while (true) {
            if (Math.floor(1 / 9 * Math.pow(10, chainDigits + 1) * digit) <= cps * 6 * 60 * 60 * goldenCookieMultiplier) //Check if we passed 6 hours of production
                chainDigits++;
            else break;
        }
        if (chainDigits <= 5) { //If max chain is lesser or equal to 5 we dont need to reserver as chain will not break before the 5 digits and 6 digits is more than 6h production.
            var res = 0;
        } else {
            res = Math.floor(1 / 9 * Math.pow(10, chainDigits) * digit) * goldenCookieMultiplier * 4; //Multiply max chain value by 4 so that it does not break the 25% barrier
        }
        return res;
    }

    this.cookieChain = function () {
        var cps = Game.frenzy > 0 ? Game.cookiesPs / Game.frenzyPower : Game.cookiesPs;
        return calculateCookieChainReserve(cps);
    };

    this.frenzyLucky = function () {
        return this.lucky() * 7;
    };

    this.frenzyCookieChain = function () {
        var cps = Game.frenzy > 0 ? Game.cookiesPs / Game.frenzyPower : Game.cookiesPs;
        return calculateCookieChainReserve(cps * 7)
    };

    this.subMax = function() {
        return Math.max(this.cookieChain(), this.frenzyLucky());
    };

    this.max = function() {
        return Math.max(this.frenzyCookieChain(), this.frenzyLucky());
    };

    this.changeReserveLevel = function() {
        this.reserveLevel++;
        if (this.reserveLevel % MAX_RESERVE === 2 && Game.elderWrath == 0 && Game.cookiesEarned < 100000) this.reserveLevel = 4; //There is no cookie chain at start of the game.
        if ((this.reserveLevel % MAX_RESERVE === 3 || this.reserveLevel % MAX_RESERVE === 4) && Game.elderWrath == 3) this.reserveLevel = 5; //Theres no frenzy on wrath cookies
        if (this.reserveLevel == MAX_RESERVE) this.reserveLevel = 0;
        this.updateReserveAmount();
        updateReserveNote();
        loop();
    };

    this.updateReserveAmount = function() {
        if (this.reserveLevel % MAX_RESERVE === 0) this.reserveAmount = 0;
        if (this.reserveLevel % MAX_RESERVE === 1) this.reserveAmount = this.lucky();
        if (this.reserveLevel % MAX_RESERVE === 2) this.reserveAmount = this.cookieChain();
        if (this.reserveLevel % MAX_RESERVE === 3) this.reserveAmount = this.frenzyCookieChain();
        if (this.reserveLevel % MAX_RESERVE === 4) this.reserveAmount = this.frenzyLucky();
        if (this.reserveLevel % MAX_RESERVE === 5) this.reserveAmount = this.subMax();
        if (this.reserveLevel % MAX_RESERVE === 6) this.reserveAmount = this.max();
    }
}

function calculateNextBuyTime() {
    var nextBuyTime = (RESERVE.reserveAmount + NEXT_BUY.price - Game.cookies) / Game.cookiesPs;
    return nextBuyTime < 0 ? 0 : nextBuyTime;
}

function buy(what) {
    if (what.unlocked == 0) {
        log("Trying to buy " + what.name + " but its not unlocked yet!");
        return;
    }
    if (what.amount == 0) {
        log("Bought the first " + what.name);
    }
    var oldBuyMode = Game.buyMode;
    Game.buyMode = 1; //Make sure we are not selling
    what.buy(1);
    Game.buyMode = oldBuyMode;
    Game.CalculateGains();
    PRODUCTION_LOG.add();
}

/**
 * Calculates best thing to buy
 * Buys best thing if possible
 * Checks if it is worth to pop a wrinkler
 */
function loop() {
    if (MAIN_INTERVAL) clearTimeout(MAIN_INTERVAL); //Prevent two or more timers from running this.
    NEXT_BUY = getBestBuy();
    if (Game.cookies >= NEXT_BUY.price + RESERVE.reserveAmount) {
        buy(NEXT_BUY.nextBuy);
    }
    updateAllNotes();
    /*if (worth)
     popWrinkler();*/
    MAIN_INTERVAL = setTimeout(loop, calculateNextBuyTime());
}

/*****************************************************************************SESSION TIME***************************************************************************/
/**
 * Session oject that tries to load the last state from localStorage at the constructor.
 */
function Session() {
    /*
     * Loads session time from local storage
     * @return {number} Returns the current session time
     */
    this.sessionTime = parseInt(localStorage.getItem(SESSION_TIME_STORAGE_NAME), 10);
    if (isNaN(this.sessionTime)) {
        if (Game.lastDate -  Game.startDate > 60000) { // Game was just loaded and therefore last save is more than 1 minute away
            this.sessionTime = Game.lastDate -  Game.startDate;
        }
        else {
            this.sessionTime = new Date().getTime() - Game.startDate;
        }
        log("Session Time not found, using estimated time of " + timeString(this.sessionTime / 1000));
    }
    this.lastSave = new Date().getTime();


    /*
     * Updates Session time and saves it in the local storage
     * @return {number} Returns the current session time
     */
    this.update = function () {
        this.sessionTime += new Date().getTime() - this.lastSave;
        localStorage.setItem(SESSION_TIME_STORAGE_NAME, this.sessionTime);
        this.lastSave = new Date().getTime();
    };
}

/********************************************************************************NOTES*******************************************************************************/
/**
 * Creates a new Note
 * @param {string} id ID for the new note
 * @return {HTMLElement} The div for the new note
 */
function newNote(id){
    var note = document.createElement("div");
    note.id = id;
    note.className = "framed";
    note.style.width = "250px";
    note.style.display = "none";
    return note;
}

/**
 * Create the notification area
 * @param {string} id ID for the note area
 * @return {HTMLElement} Div for the notes
 */
function createNotesArea(id) {
    var note_area = document.getElementById(id);
    if (note_area !== null) note_area.parentNode.removeChild(note_area);
    var notes = document.getElementById("notes");
    var newDiv = document.createElement("div");
    newDiv.id = id;
    newDiv.style.position = "absolute";
    newDiv.style.bottom = "15px";
    newDiv.style.left = "auto";
    newDiv.style.width = "250px";
    newDiv.style.zIndex = 100000001;
    notes.parentElement.insertBefore(newDiv, notes);
    return newDiv;
}

/**
 * Creates the notification area and notes
 */
function createNotes() {
    var notification_area = createNotesArea(NOTE_AREA_ID);
    var next_buy_note = newNote(NEXT_BUY_NOTE_ID);
    var goal_note = newNote(GOAL_NOTE_ID);
    var reserve_note = newNote(RESERVE_NOTE_ID);
    var spawn_window_note = newNote(SPAWN_WINDOW_NOTE_ID);
    notification_area.appendChild(spawn_window_note);
    notification_area.appendChild(reserve_note);
    notification_area.appendChild(goal_note);
    notification_area.appendChild(next_buy_note);
    log("All notes created successfully.");
}

function updateNote(note, title, description, display) {
    if (note.style.display != display) note.style.display = display;
    note.innerHTML = "<div class='text'><h3>" + title + "</h3><h5><div class='title' style='font-size:16px;'>" + description + "</div></h5></div>";
}

/**
 *
 * @param {Building} bestBuy
 * @param reserve
 */
function updateNextBuyNote() {
    //Maybe change remain cost
    if (!NEXT_BUY) return; //Next buy still not set
    var nextBuy = NEXT_BUY.nextBuy;
    var note = document.getElementById(NEXT_BUY_NOTE_ID);
    //var nextBuyTime = getNextBuyTime(bestBuy, reserve);
    var nextBuyTime = calculateNextBuyTime();
    var title = "Next buy in " + timeString(nextBuyTime);
    if (Math.floor(nextBuyTime) === 0) {
        if (nextBuy instanceof Building) {
            title = "Buying the " + convertNumeral(nextBuy.bought + 1);
        } else { //Upgrade
            title = "Buying "
        }
    }
    var description = nextBuy.name;
    var display = "block";
    if (Game.cookiesPs === 0) title = "No production click cookie to buy";
    updateNote(note, title, description, display);
}

function updateGoalNote() {
    var note = document.getElementById(GOAL_NOTE_ID);
    if (typeof NEXT_BUY === 'undefined' && note.display == "none") return;
    var title = "Goal";
    var description = "";
    var display = "none";
    if (typeof NEXT_BUY !== 'undefined' && NEXT_BUY.name != NEXT_BUY.nextBuy.name) {
        description = NEXT_BUY.name;
        display = "block";
    }
    updateNote(note, title, description, display);
}

function updateReserveNote() {
    var note = document.getElementById(RESERVE_NOTE_ID);
    var description = Beautify(RESERVE.reserveAmount);
    var color = "#6F6";
    var title = "Reserve ";
    if (RESERVE.reserveLevel === 0) {
        color = "#F66";
        title += "(disabled)";
    }
    if (RESERVE.reserveLevel === 1) title += "(Lucky!)";
    if (RESERVE.reserveLevel === 2) title += "(Cookie Chain)";
    if (RESERVE.reserveLevel === 3) title += "(Frenzy + Cookie Chain)";
    if (RESERVE.reserveLevel === 4) title += "(Lucky + Frenzy)";
    if (RESERVE.reserveLevel === 5) title += "(Chain or Lucky + Frenzy)";
    if (RESERVE.reserveLevel === 6) title += "(All)";
    note.style.display = "block";
    note.innerHTML = "<div class='text' style='color: " + color + "'><h3>" + title + "<a onClick = 'RESERVE.changeReserveLevel();' style='float: right; text-decoration: none; font-size: 30px;'>?</a></h3><h5><div class='title' style='font-size:16px;'>" + description + "</div></h5></div>";
}

function updateCookieSpawnWindowNote() {
    var note = document.getElementById(SPAWN_WINDOW_NOTE_ID);
    var display = "block";
    if (RESERVE.reserveLevel === 1 === 0) display = "none";
    var title = "Cookie spawn window ends in";
    var description = timeString((Game.goldenCookie.maxTime - Game.goldenCookie.time) / Game.fps);
    if (Game.goldenCookie.minTime > Game.goldenCookie.time) {
        title = "Next cookie spawn window in";
        description = timeString((Game.goldenCookie.minTime - Game.goldenCookie.time) / Game.fps);
    }
    if (Game.goldenCookie.time === 0 && RESERVE.reserveLevel === 1 !== 0) {
        title = "Cookie despawning in";
        description = timeString(Game.goldenCookie.life / Game.fps);
        document.title = description + " until cookie despawn";
    }
    updateNote(note, title, description, display);
}

function updateAllNotes() {
    updateNextBuyNote();
    updateGoalNote();
    //updateReserveNote();
    updateCookieSpawnWindowNote();
}

function engageHooks() {
    document.getElementById("bigCookie").addEventListener("click", loop); //Hook big cookie
    document.getElementById("store").addEventListener("click", loop); //Hook store items and buildings
    document.getElementById("goldenCookie").addEventListener("click", loop); //Hook golden cookie clicks
    Game.Win = function() {
        if (typeof arguments[0] === 'string') {
            var achievement = Game.Achievements[arguments[0]];
            if (achievement) {
                if (achievement.won == 0) {
                    var runLoop = true;
                }
            }
        }
        GAME_WIN_FUNCTION.apply(this, arguments);
        if (runLoop) {
            loop();
        }
    }; //Hook achievement winning
    Game.Ascend = function() {
        GAME_ASCEND_FUNCTION.apply(this, arguments);
        if (arguments[0] === 1) { //We are really ascending not just showing popup
            stopAutoCookie();
        }
    };
    Game.Reincarnate = function() {
        GAME_REINCARNATE_FUNCTION.apply(this, arguments);
        if (arguments[0] === 1) { //We are really reincarnating not just showing popup
            startAutoCookie();
        }
    };
}

function removeHooks() {
    document.getElementById("bigCookie").removeEventListener("click", loop); //Remove big cookie hook
    document.getElementById("store").removeEventListener("click", loop); //Remove store items and buildings hook
    document.getElementById("goldenCookie").removeEventListener("click", loop); //Remove golden cookie clicks hook
    Game.Win = GAME_WIN_FUNCTION;
}

function stopAutoCookie() {
    STOPPED = 1;
    clearInterval(SAVE_INTERVAL);
    clearInterval(NOTE_UPDATE_INTERVAL);
    clearInterval(MAIN_INTERVAL);
    removeHooks();
    document.getElementById(NOTE_AREA_ID).hidden = true; //Hide notes
}

function startAutoCookie() {
    if (STOPPED) {
        STOPPED = 0;
        updateReserveNote();
        loop(); //Run the first calculation.
        SAVE_INTERVAL = setInterval(CURRENT_SESSION.update, 1000 * 60); //Saves session every minute
        NOTE_UPDATE_INTERVAL = setInterval(updateAllNotes, 1000 / 2); //Update notes every half second.
        engageHooks();
        document.getElementById(NOTE_AREA_ID).hidden = false; //Show notes
    }
}

var timedRunTimeout;

function freeze() {
    Game.Loop = 0;
    stopAutoCookie();
}

function unfreeze(catchup) {
    Game.Loop = GAME_LOOP_FUNCTION;
    if (catchup) {
        Game.time = Date.now();
    }
    Game.Loop(); //Restart game
    startAutoCookie();
}

function endTimedRun() {
    stopAutoCookie();
    clearTimeout(timedRunTimeout);
    freeze();
    log("Timed run ended!");
    log("Cookies at the end " + Game.cookies);
}

function startTimedRun(hours, minutes, duration, heavenlyChips) {
    duration = duration * 60 * 60 * 1000;
    var startDate = new Date();
    startDate.setHours(hours, minutes, 0, 0);
    var startDelay = startDate - new Date();
    if (timedRunTimeout || startDelay < 0 || duration <= 0) return;
    timedRunTimeout = setTimeout(function(){
        Game.HardReset(2);
        log("Timed run started");
        Game.CloseNotes();
        Game.prestige = heavenlyChips;
        Game.cookieClicks = 15;
        Game.cookiesEarned = 15;
        Game.cookies = 15;
        startAutoCookie();
        PRODUCTION_LOG.add();
    }, startDelay);
    setTimeout(endTimedRun, startDelay + duration);
    log("Timed run start at: " + startDate.toString());
    log("Timed run ends  at: " + new Date(startDate.getTime() + duration).toString());
    return timedRunTimeout;
}

Game.Win('Third-party'); //Give the third party achievement
createNotes();
CURRENT_SESSION = new Session();
RESERVE = new Reserve();
log("Load successful!");

startAutoCookie();

/*var upgrades = getUpgrades();
for (var i in upgrades) {
    var upgrade = upgrades[i];
    console.log(upgrade)
}*/