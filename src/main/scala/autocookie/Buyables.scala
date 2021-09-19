package autocookie

import autocookie.buyable.*
import autocookie.buyable.Building.*
import autocookie.buyable.upgrade.*

object Buyables {
  def createBuildings: Map[String, Building] =
    Building.all.map(building => building.name -> new Building(building.name)).toMap

  def createUpgrades: Map[String, Upgrade] =
    Seq(
      //region Legacy
      new LegacyUpgrade("Legacy"),
      new LegacyUpgrade("How to bake your dragon", "Legacy"),
      new LegacyUpgrade("Classic dairy selection", "Legacy"),
      new LegacyUpgrade("Basic wallpaper assortment", "Classic dairy selection"),
      new LegacyUpgrade("Fanciful dairy selection", "Classic dairy selection"),
      new LegacyUpgrade("Heralds", "Legacy"),

      new LegacyUpgrade("Twin Gates of Transcendence", "Legacy"),
      new LegacyUpgrade("Angels", "Twin Gates of Transcendence"),
      new LegacyUpgrade("Archangels", "Angels"),
      new LegacyUpgrade("Virtues", "Archangels"),
      new LegacyUpgrade("Dominions", "Virtues"),
      new LegacyUpgrade("Cherubim", "Dominions"),
      new LegacyUpgrade("Kitten angels", "Dominions"),
      new LegacyUpgrade("Seraphim", "Cherubim"),
      new LegacyUpgrade("God", "Seraphim"),
      new LegacyUpgrade("Belphegor", "Twin Gates of Transcendence"),
      new LegacyUpgrade("Mammon", "Belphegor"),
      new LegacyUpgrade("Abaddon", "Mammon"),
      new LegacyUpgrade("Satan", "Abaddon"),
      new LegacyUpgrade("Asmodeus", "Satan"),
      new LegacyUpgrade("Beelzebub", "Asmodeus"),
      new LegacyUpgrade("Lucifer", "Beelzebub"),
      new LegacyUpgrade("Synergies Vol. I", "Dominions", "Satan"),
      new LegacyUpgrade("Synergies Vol. II", "Synergies Vol. I", "Seraphim", "Beelzebub"),
      new LegacyUpgrade("Chimera", "Synergies Vol. II", "God", "Lucifer"),

      new LegacyUpgrade("Persistent memory", "Legacy"),
      new LegacyUpgrade("Permanent upgrade slot I", "Legacy"),
      new LegacyUpgrade("Permanent upgrade slot II", "Permanent upgrade slot I"),
      new LegacyUpgrade("Permanent upgrade slot III", "Permanent upgrade slot II"),
      new LegacyUpgrade("Permanent upgrade slot IV", "Permanent upgrade slot III"),
      new LegacyUpgrade("Permanent upgrade slot V", "Permanent upgrade slot IV"),
      new LegacyUpgrade("Inspired checklist", "Persistent memory", "Permanent upgrade slot IV"),
      new LegacyUpgrade("Genius accounting", "Inspired checklist"),

      new LegacyUpgrade("Heavenly luck", "Legacy"),
      new LegacyUpgrade("Lasting fortune", "Heavenly luck"),
      new LegacyUpgrade("Decisive fate", "Lasting fortune"),
      new LegacyUpgrade("Golden switch", "Heavenly luck"),
      new LegacyUpgrade("Lucky digit", "Heavenly luck"),
      new LegacyUpgrade("Lucky number", "Lucky digit", "Lasting fortune"),
      new LegacyUpgrade("Lucky payout", "Lucky number", "Decisive fate"),
      new LegacyUpgrade("Residual luck", "Golden switch"),
      new LegacyUpgrade("Golden cookie alert sound", "Golden switch", "Decisive fate"),
      new LegacyUpgrade("Divine discount", "Decisive fate"),
      new LegacyUpgrade("Divine sales", "Decisive fate"),
      new LegacyUpgrade("Divine bakeries", "Divine discount", "Divine sales"),
      new LegacyUpgrade("Distilled essence of redoubled luck", "Divine bakeries", "Residual luck"),
      new LegacyUpgrade("Shimmering veil", "Distilled essence of redoubled luck"),
      new LegacyUpgrade("Cosmic beginner's luck", "Shimmering veil"),
      new LegacyUpgrade("Reinforced membrane", "Shimmering veil"),

      new LegacyUpgrade("Heavenly cookies", "Legacy"),
      new LegacyUpgrade("Tin of british tea biscuits", "Heavenly cookies"),
      new LegacyUpgrade("Box of macarons", "Heavenly cookies"),
      new LegacyUpgrade("Box of brand biscuits", "Heavenly cookies"),
      new LegacyUpgrade("Tin of butter cookies", "Heavenly cookies"),
      new LegacyUpgrade("Starter kit", "Tin of british tea biscuits", "Box of macarons", "Box of brand biscuits", 
        "Tin of butter cookies"),
      new LegacyUpgrade("Starter kitchen", "Starter kit"),
      new LegacyUpgrade("Unholy bait", "Starter kitchen"),
      new LegacyUpgrade("Elder spice", "Unholy bait"),
      new LegacyUpgrade("Sacrilegious corruption", "Unholy bait"),
      new LegacyUpgrade("Wrinkly cookies", "Sacrilegious corruption", "Elder spice"),
      new LegacyUpgrade("Stevia Caelestis", "Wrinkly cookies"),
      new LegacyUpgrade("Sugar baking", "Stevia Caelestis"),
      new LegacyUpgrade("Sugar crystal cookies", "Sugar baking"),
      new LegacyUpgrade("Box of maybe cookies", "Sugar crystal cookies"),
      new LegacyUpgrade("Box of not cookies", "Sugar crystal cookies"),
      new LegacyUpgrade("Box of pastries", "Sugar crystal cookies"),
      //endregion,
      //region Cursor
      new BuildingUpgradeWithAchievement(
        "Reinforced index finger", new BuildingRequirement(
          Building
            .cursor, 1
        ), "Click"
      ),
      new BuildingUpgradeWithAchievement(
        "Carpal tunnel prevention cream", new BuildingRequirement(
          Building
            .cursor, 1
        ), "Click"
      ),
      new BuildingUpgrade("Ambidextrous", new BuildingRequirement(Building.cursor, 10)),
      new BuildingUpgrade("Thousand fingers", new BuildingRequirement(Building.cursor, 25)),
      new BuildingUpgradeWithAchievement(
        "Million fingers", new BuildingRequirement(
          Building
            .cursor, 50
        ), "Mouse wheel"
      ),
      new BuildingUpgradeWithAchievement(
        "Billion fingers", new BuildingRequirement(
          Building
            .cursor, 100
        ), "Of Mice and Men"
      ),
      new BuildingUpgrade("Trillion fingers", new BuildingRequirement(Building.cursor, 150)),
      new BuildingUpgradeWithAchievement(
        "Quadrillion fingers", new BuildingRequirement(
          Building
            .cursor, 200
        ), "The Digital"
      ),
      new BuildingUpgrade("Quintillion fingers", new BuildingRequirement(Building.cursor, 250)),
      new BuildingUpgradeWithAchievement(
        "Sextillion fingers", new BuildingRequirement(
          Building
            .cursor, 300
        ), "Extreme polydactyly"
      ),
      new BuildingUpgrade("Septillion fingers", new BuildingRequirement(Building.cursor, 350)),
      new BuildingUpgradeWithAchievement("Octillion fingers", new BuildingRequirement(Building.cursor, 400), "Dr. T"),
      new BuildingUpgrade("Nonillion fingers", new BuildingRequirement(Building.cursor, 450)),
      //endregion,
      //region Grandma
      new BuildingUpgradeWithAchievement(
        "Forwards from grandma", new BuildingRequirement(
          Building
            .grandma, 1
        ), "Grandma\'s cookies"
      ),
      new BuildingUpgrade("Steel-plated rolling pins", new BuildingRequirement(Building.grandma, 5)),
      new BuildingUpgrade("Lubricated dentures", new BuildingRequirement(Building.grandma, 25)),
      new BuildingUpgradeWithAchievement("Prune juice", new BuildingRequirement(Building.grandma, 50), "Sloppy kisses"),
      new BuildingUpgradeWithAchievement(
        "Double-thick glasses", new BuildingRequirement(
          Building
            .grandma, 100
        ), "Retirement home"
      ),
      new BuildingUpgradeWithAchievement(
        "Aging agents", new BuildingRequirement(
          Building
            .grandma, 150
        ), "Friend of the ancients"
      ),
      new BuildingUpgradeWithAchievement(
        "Xtreme walkers", new BuildingRequirement(
          Building
            .grandma, 200
        ), "Ruler of the ancients"
      ),
      new BuildingUpgradeWithAchievement(
        "The Unbridling", new BuildingRequirement(
          Building
            .grandma, 250
        ), "The old never bothered me anyway"
      ),
      new BuildingUpgradeWithAchievement(
        "Reverse dementia", new BuildingRequirement(
          Building
            .grandma, 300
        ), "The agemaster"
      ),
      new BuildingUpgradeWithAchievement(
        "Timeproof hair dyes", new BuildingRequirement(
          Building
            .grandma, 350
        ), "To oldly go"
      ),
      new BuildingUpgradeWithAchievement("Good manners", new BuildingRequirement(Building.grandma, 400), "Aged well"),
      new BuildingUpgradeWithAchievement(
        "Generation degeneration", new BuildingRequirement(
          Building
            .grandma, 450
        ), "101st birthday"
      ),
      new BuildingUpgradeWithAchievement(
        "Visits", new BuildingRequirement(
          Building
            .grandma, 500
        ), "Defense of the ancients"
      ),

      new BuildingUpgrade(
        "Farmer grandmas", new BuildingRequirement(
          Building
            .grandma, 1
        ), new BuildingRequirement(Building.farm, 15)
      ),
      new BuildingUpgrade(
        "Miner grandmas", new BuildingRequirement(
          Building
            .grandma, 1
        ), new BuildingRequirement(Building.mine, 15)
      ),
      new BuildingUpgrade(
        "Worker grandmas", new BuildingRequirement(
          Building
            .grandma, 1
        ), new BuildingRequirement(Building.factory, 15)
      ),
      new BuildingUpgrade(
        "Banker grandmas", new BuildingRequirement(
          Building
            .grandma, 1
        ), new BuildingRequirement(Building.bank, 15)
      ),
      new BuildingUpgrade(
        "Priestess grandmas", new BuildingRequirement(
          Building
            .grandma, 1
        ), new BuildingRequirement(Building.temple, 15)
      ),
      new BuildingUpgrade(
        "Witch grandmas", new BuildingRequirement(
          Building
            .grandma, 1
        ), new BuildingRequirement(Building.wizardTower, 15)
      ),
      new BuildingUpgrade(
        "Cosmic grandmas", new BuildingRequirement(
          Building
            .grandma, 1
        ), new BuildingRequirement(Building.shipment, 15)
      ),
      new BuildingUpgrade(
        "Transmuted grandmas", new BuildingRequirement(
          Building
            .grandma, 1
        ), new BuildingRequirement(Building.alchemyLab, 15)
      ),
      new BuildingUpgrade(
        "Altered grandmas", new BuildingRequirement(
          Building
            .grandma, 1
        ), new BuildingRequirement(Building.portal, 15)
      ),
      new BuildingUpgrade(
        "Grandmas\' grandmas", new BuildingRequirement(
          Building
            .grandma, 1
        ), new BuildingRequirement(Building.timeMachine, 15)
      ),
      new BuildingUpgrade(
        "Antigrandmas", new BuildingRequirement(Building.grandma, 1), new BuildingRequirement(
          Building
            .antimatterCondenser, 15
        )
      ),
      new BuildingUpgrade(
        "Rainbow grandmas", new BuildingRequirement(
          Building
            .grandma, 1
        ), new BuildingRequirement(Building.prism, 15)
      ),
      new BuildingUpgrade(
        "Lucky grandmas", new BuildingRequirement(
          Building
            .grandma, 1
        ), new BuildingRequirement(Building.chancemaker, 15)
      ),
      new BuildingUpgrade(
        "Metagrandmas", new BuildingRequirement(Building.grandma, 1), new BuildingRequirement(
          Building
            .fractalEngine, 15
        )
      ),
      //endregion,
      //region Farm
      new BuildingUpgradeWithAchievement("Cheap hoes", new BuildingRequirement(Building.farm, 1), "Bought the farm"),
      new BuildingUpgrade("Fertilizer", new BuildingRequirement(Building.farm, 5)),
      new BuildingUpgrade("Cookie trees", new BuildingRequirement(Building.farm, 25)),
      new BuildingUpgradeWithAchievement(
        "Genetically-modified cookies", new BuildingRequirement(
          Building
            .farm, 50
        ), "Reap what you sow"
      ),
      new BuildingUpgradeWithAchievement(
        "Gingerbread scarecrows", new BuildingRequirement(
          Building
            .farm, 100
        ), "Farm ill"
      ),
      new BuildingUpgradeWithAchievement(
        "Pulsar sprinklers", new BuildingRequirement(
          Building
            .farm, 150
        ), "Perfected agriculture"
      ),
      new BuildingUpgradeWithAchievement("Fudge fungus", new BuildingRequirement(Building.farm, 200), "Homegrown"),
      new BuildingUpgradeWithAchievement(
        "Wheat triffids", new BuildingRequirement(
          Building
            .farm, 250
        ), "Gardener extraordinaire"
      ),
      new BuildingUpgradeWithAchievement(
        "Humane pesticides", new BuildingRequirement(
          Building
            .farm, 300
        ), "Seedy business"
      ),
      new BuildingUpgradeWithAchievement(
        "Barnstars", new BuildingRequirement(
          Building
            .farm, 350
        ), "You and the beanstalk"
      ),
      new BuildingUpgradeWithAchievement("Lindworms", new BuildingRequirement(Building.farm, 400), "Harvest moon"),
      new BuildingUpgradeWithAchievement(
        "Global seed vault", new BuildingRequirement(
          Building
            .farm, 450
        ), "Make like a tree"
      ),
      new BuildingUpgradeWithAchievement(
        "Reverse-veganism", new BuildingRequirement(
          Building
            .farm, 500
        ), "Sharpest tool in the shed"
      ),
      //endregion,
      //region Mine
      new BuildingUpgradeWithAchievement("Sugar gas", new BuildingRequirement(Building.mine, 1), "You know the drill"),
      new BuildingUpgrade("Megadrill", new BuildingRequirement(Building.mine, 5)),
      new BuildingUpgrade("Ultradrill", new BuildingRequirement(Building.mine, 25)),
      new BuildingUpgradeWithAchievement("Ultimadrill", new BuildingRequirement(Building.mine, 50), "Excavation site"),
      new BuildingUpgradeWithAchievement(
        "H-bomb mining", new BuildingRequirement(
          Building
            .mine, 100
        ), "Hollow the planet"
      ),
      new BuildingUpgradeWithAchievement("Coreforge", new BuildingRequirement(Building.mine, 150), "Can you dig it"),
      new BuildingUpgradeWithAchievement(
        "Planetsplitters", new BuildingRequirement(
          Building
            .mine, 200
        ), "The center of the Earth"
      ),
      new BuildingUpgradeWithAchievement(
        "Canola oil wells", new BuildingRequirement(
          Building
            .mine, 250
        ), "Tectonic ambassador"
      ),
      new BuildingUpgradeWithAchievement("Mole people", new BuildingRequirement(Building.mine, 300), "Freak fracking"),
      new BuildingUpgradeWithAchievement(
        "Mine canaries", new BuildingRequirement(
          Building
            .mine, 350
        ), "Romancing the stone"
      ),
      new BuildingUpgradeWithAchievement("Bore again", new BuildingRequirement(Building.mine, 400), "Mine?"),
      new BuildingUpgradeWithAchievement("Air mining", new BuildingRequirement(Building.mine, 450), "Cave story"),
      new BuildingUpgradeWithAchievement(
        "Caramel alloys", new BuildingRequirement(
          Building
            .mine, 500
        ), "Hey now, you're a rock"
      ),
      //endregion,
      //region Factory
      new BuildingUpgradeWithAchievement(
        "Sturdier conveyor belts", new BuildingRequirement(
          Building
            .factory, 1
        ), "Production chain"
      ),
      new BuildingUpgrade("Child labor", new BuildingRequirement(Building.factory, 5)),
      new BuildingUpgrade("Sweatshop", new BuildingRequirement(Building.factory, 25)),
      new BuildingUpgradeWithAchievement(
        "Radium reactors", new BuildingRequirement(
          Building
            .factory, 50
        ), "Industrial revolution"
      ),
      new BuildingUpgradeWithAchievement(
        "Recombobulators", new BuildingRequirement(
          Building
            .factory, 100
        ), "Global warming"
      ),
      new BuildingUpgradeWithAchievement(
        "Deep-bake process", new BuildingRequirement(
          Building
            .factory, 150
        ), "Ultimate automation"
      ),
      new BuildingUpgradeWithAchievement(
        "Cyborg workforce", new BuildingRequirement(
          Building
            .factory, 200
        ), "Technocracy"
      ),
      new BuildingUpgradeWithAchievement(
        "78-hour days", new BuildingRequirement(
          Building
            .factory, 250
        ), "Rise of the machines"
      ),
      new BuildingUpgradeWithAchievement(
        "Machine learning", new BuildingRequirement(
          Building
            .factory, 300
        ), "Modern times"
      ),
      new BuildingUpgradeWithAchievement(
        "Brownie point system", new BuildingRequirement(
          Building
            .factory, 350
        ), "Ex machina"
      ),
      new BuildingUpgradeWithAchievement(
        "\"Volunteer\" interns", new BuildingRequirement(
          Building
            .factory, 400
        ), "In full gear"
      ),
      new BuildingUpgradeWithAchievement(
        "Behavioral reframing", new BuildingRequirement(
          Building
            .factory, 450
        ), "In-cog-neato"
      ),
      new BuildingUpgradeWithAchievement(
        "The infinity engine", new BuildingRequirement(
          Building
            .factory, 500
        ), "Break the mold"
      ),
      //endregion,
      //region Bank
      new BuildingUpgradeWithAchievement("Taller tellers", new BuildingRequirement(Building.bank, 1), "Pretty penny"),
      new BuildingUpgrade("Scissor-resistant credit cards", new BuildingRequirement(Building.bank, 5)),
      new BuildingUpgrade("Acid-proof vaults", new BuildingRequirement(Building.bank, 25)),
      new BuildingUpgradeWithAchievement("Chocolate coins", new BuildingRequirement(Building.bank, 50), "Fit the bill"),
      new BuildingUpgradeWithAchievement(
        "Exponential interest rates", new BuildingRequirement(
          Building
            .bank, 100
        ), "A loan in the dark"
      ),
      new BuildingUpgradeWithAchievement(
        "Financial zen", new BuildingRequirement(
          Building
            .bank, 150
        ), "Need for greed"
      ),
      new BuildingUpgradeWithAchievement(
        "Way of the wallet", new BuildingRequirement(
          Building
            .bank, 200
        ), "It\'s the economy, stupid"
      ),
      new BuildingUpgradeWithAchievement(
        "The stuff rationale", new BuildingRequirement(
          Building
            .bank, 250
        ), "Acquire currency"
      ),
      new BuildingUpgradeWithAchievement(
        "Edible money", new BuildingRequirement(
          Building
            .bank, 300
        ), "The nerve of war"
      ),
      new BuildingUpgradeWithAchievement(
        "Grand supercycles", new BuildingRequirement(
          Building
            .bank, 350
        ), "And I need it now"
      ),
      new BuildingUpgradeWithAchievement(
        "Rules of acquisition", new BuildingRequirement(
          Building
            .bank, 400
        ), "Treacle tart economics"
      ),
      new BuildingUpgradeWithAchievement(
        "Altruistic loop", new BuildingRequirement(
          Building
            .bank, 450
        ), "Save your breath because that's all you've got left"
      ),
      new BuildingUpgradeWithAchievement(
        "Diminishing tax returns", new BuildingRequirement(
          Building
            .bank, 500
        ), "Get the show on, get paid"
      ),
      //endregion,
      //region Temple
      new BuildingUpgradeWithAchievement(
        "Golden idols", new BuildingRequirement(
          Building
            .temple, 1
        ), "Your time to shrine"
      ),
      new BuildingUpgrade("Sacrifices", new BuildingRequirement(Building.temple, 5)),
      new BuildingUpgrade("Delicious blessing", new BuildingRequirement(Building.temple, 25)),
      new BuildingUpgradeWithAchievement("Sun festival", new BuildingRequirement(Building.temple, 50), "New-age cult"),
      new BuildingUpgradeWithAchievement(
        "Enlarged pantheon", new BuildingRequirement(
          Building
            .temple, 100
        ), "New-age cult"
      ),
      new BuildingUpgradeWithAchievement(
        "Great Baker in the sky", new BuildingRequirement(
          Building
            .temple, 150
        ), "Organized religion"
      ),
      new BuildingUpgradeWithAchievement("Creation myth", new BuildingRequirement(Building.temple, 200), "Fanaticism"),
      new BuildingUpgradeWithAchievement("Theocracy", new BuildingRequirement(Building.temple, 250), "Zealotry"),
      new BuildingUpgradeWithAchievement("Sick rap prayers", new BuildingRequirement(Building.temple, 300), "Wololo"),
      new BuildingUpgradeWithAchievement(
        "Psalm-reading", new BuildingRequirement(
          Building
            .temple, 350
        ), "Pray on the weak"
      ),
      new BuildingUpgradeWithAchievement(
        "War of the gods", new BuildingRequirement(
          Building
            .temple, 400
        ), "Holy cookies, grandma!"
      ),
      new BuildingUpgradeWithAchievement(
        "A novel idea", new BuildingRequirement(
          Building
            .temple, 450
        ), "Vengeful and almighty"
      ),
      new BuildingUpgradeWithAchievement(
        "Apparitions", new BuildingRequirement(
          Building
            .temple, 500
        ), "My world's on fire, how about yours"
      ),
      //endregion,
      //region Wizard tower,
      new BuildingUpgradeWithAchievement(
        "Pointier hats", new BuildingRequirement(
          Building
            .wizardTower, 1
        ), "Bewitched"
      ),
      new BuildingUpgrade("Beardlier beards", new BuildingRequirement(Building.wizardTower, 5)),
      new BuildingUpgrade("Ancient grimoires", new BuildingRequirement(Building.wizardTower, 25)),
      new BuildingUpgradeWithAchievement(
        "Kitchen curses", new BuildingRequirement(
          Building
            .wizardTower, 50
        ), "The sorcerer\'s apprentice"
      ),
      new BuildingUpgradeWithAchievement(
        "School of sorcery", new BuildingRequirement(
          Building
            .wizardTower, 100
        ), "Charms and enchantments"
      ),
      new BuildingUpgradeWithAchievement(
        "Dark formulas", new BuildingRequirement(
          Building
            .wizardTower, 150
        ), "Curses and maledictions"
      ),
      new BuildingUpgradeWithAchievement(
        "Cookiemancy", new BuildingRequirement(
          Building
            .wizardTower, 200
        ), "Magic kingdom"
      ),
      new BuildingUpgradeWithAchievement(
        "Rabbit trick", new BuildingRequirement(
          Building
            .wizardTower, 250
        ), "The wizarding world"
      ),
      new BuildingUpgradeWithAchievement(
        "Deluxe tailored wands", new BuildingRequirement(
          Building
            .wizardTower, 300
        ), "And now for my next trick, I'll need a volunteer from the audience"
      ),
      new BuildingUpgradeWithAchievement(
        "Immobile spellcasting", new BuildingRequirement(
          Building
            .wizardTower, 350
        ), "It's a kind of magic"
      ),
      new BuildingUpgradeWithAchievement(
        "Electricity", new BuildingRequirement(
          Building
            .wizardTower, 400
        ), "The Prestige"
      ),
      new BuildingUpgradeWithAchievement(
        "Spelling bees", new BuildingRequirement(
          Building
            .wizardTower, 450
        ), "Spell it out for you"
      ),
      new BuildingUpgradeWithAchievement(
        "Wizard basements", new BuildingRequirement(
          Building
            .wizardTower, 500
        ), "The meteor men beg to differ"
      ),
      //endregion,
      //region Shipment
      new BuildingUpgradeWithAchievement(
        "Vanilla nebulae", new BuildingRequirement(
          Building
            .shipment, 1
        ), "Expedition"
      ),
      new BuildingUpgrade("Wormholes", new BuildingRequirement(Building.shipment, 5)),
      new BuildingUpgrade("Frequent flyer", new BuildingRequirement(Building.shipment, 25)),
      new BuildingUpgradeWithAchievement(
        "Warp drive", new BuildingRequirement(
          Building
            .shipment, 50
        ), "Galactic highway"
      ),
      new BuildingUpgradeWithAchievement(
        "Chocolate monoliths", new BuildingRequirement(
          Building
            .shipment, 100
        ), "Far far away"
      ),
      new BuildingUpgradeWithAchievement(
        "Generation ship", new BuildingRequirement(
          Building
            .shipment, 150
        ), "Type II civilization"
      ),
      new BuildingUpgradeWithAchievement(
        "Dyson sphere", new BuildingRequirement(
          Building
            .shipment, 200
        ), "We come in peace"
      ),
      new BuildingUpgradeWithAchievement(
        "The final frontier", new BuildingRequirement(
          Building
            .shipment, 250
        ), "Parsec-masher"
      ),
      new BuildingUpgradeWithAchievement(
        "Autopilot", new BuildingRequirement(
          Building
            .shipment, 300
        ), "It's not delivery"
      ),
      new BuildingUpgradeWithAchievement(
        "Restaurants at the end of the universe", new BuildingRequirement(
          Building
            .shipment, 350
        ), "Make it so"
      ),
      new BuildingUpgradeWithAchievement(
        "Universal alphabet", new BuildingRequirement(
          Building
            .shipment, 400
        ), "That's just peanuts to space"
      ),
      new BuildingUpgradeWithAchievement(
        "Toroid universe", new BuildingRequirement(
          Building
            .shipment, 450
        ), "Space space space space space"
      ),
      new BuildingUpgradeWithAchievement(
        "Prime directive", new BuildingRequirement(
          Building
            .shipment, 500
        ), "Only shooting stars"
      ),
      //endregion,
      //region Alchemy lab,
      new BuildingUpgradeWithAchievement("Antimony", new BuildingRequirement(Building.alchemyLab, 1), "Transmutation"),
      new BuildingUpgrade("Essence of dough", new BuildingRequirement(Building.alchemyLab, 5)),
      new BuildingUpgrade("True chocolate", new BuildingRequirement(Building.alchemyLab, 25)),
      new BuildingUpgradeWithAchievement(
        "Ambrosia", new BuildingRequirement(
          Building
            .alchemyLab, 50
        ), "Transmogrification"
      ),
      new BuildingUpgradeWithAchievement(
        "Aqua crustulae", new BuildingRequirement(
          Building
            .alchemyLab, 100
        ), "Gold member"
      ),
      new BuildingUpgradeWithAchievement(
        "Origin crucible", new BuildingRequirement(
          Building
            .alchemyLab, 150
        ), "Gild wars"
      ),
      new BuildingUpgradeWithAchievement(
        "Theory of atomic fluidity", new BuildingRequirement(
          Building
            .alchemyLab, 200
        ), "The secrets of the universe"
      ),
      new BuildingUpgradeWithAchievement(
        "Beige goo", new BuildingRequirement(
          Building
            .alchemyLab, 250
        ), "The work of a lifetime"
      ),
      new BuildingUpgradeWithAchievement(
        "The advent of chemistry", new BuildingRequirement(
          Building
            .alchemyLab, 300
        ), "Gold, Jerry! Gold!"
      ),
      new BuildingUpgradeWithAchievement(
        "On second thought", new BuildingRequirement(
          Building
            .alchemyLab, 350
        ), "All that glitters is gold"
      ),
      new BuildingUpgradeWithAchievement(
        "Public betterment", new BuildingRequirement(
          Building
            .alchemyLab, 400
        ), "Worth its weight in lead"
      ),
      new BuildingUpgradeWithAchievement(
        "Hermetic reconciliation", new BuildingRequirement(
          Building
            .alchemyLab, 450
        ), "Don't get used to yourself, you're gonna have to change"
      ),
      new BuildingUpgradeWithAchievement(
        "Chromatic cycling", new BuildingRequirement(
          Building
            .alchemyLab, 500
        ), "We could all use a little change"
      ),
      //endregion,
      //region Portal
      new BuildingUpgradeWithAchievement(
        "Ancient tablet", new BuildingRequirement(
          Building
            .portal, 1
        ), "A whole new world"
      ),
      new BuildingUpgrade("Insane oatling workers", new BuildingRequirement(Building.portal, 5)),
      new BuildingUpgrade("Soul bond", new BuildingRequirement(Building.portal, 25)),
      new BuildingUpgradeWithAchievement(
        "Sanity dance", new BuildingRequirement(
          Building
            .portal, 50
        ), "Now you\'re thinking"
      ),
      new BuildingUpgradeWithAchievement(
        "Brane transplant", new BuildingRequirement(
          Building
            .portal, 100
        ), "Dimensional shift"
      ),
      new BuildingUpgradeWithAchievement(
        "Deity-sized portals", new BuildingRequirement(
          Building
            .portal, 150
        ), "Brain-split"
      ),
      new BuildingUpgradeWithAchievement(
        "End of times back-up plan", new BuildingRequirement(
          Building
            .portal, 200
        ), "Realm of the Mad God"
      ),
      new BuildingUpgradeWithAchievement(
        "Maddening chants", new BuildingRequirement(
          Building
            .portal, 250
        ), "A place lost in time"
      ),
      new BuildingUpgradeWithAchievement(
        "The real world", new BuildingRequirement(
          Building
            .portal, 300
        ), "Forbidden zone"
      ),
      new BuildingUpgradeWithAchievement(
        "Dimensional garbage gulper", new BuildingRequirement(
          Building
            .portal, 350
        ), "H̸̷͓̳̳̯̟͕̟͍͍̣͡ḛ̢̦̰̺̮̝͖͖̘̪͉͘͡ ̠̦͕̤̪̝̥̰̠̫̖̣͙̬͘ͅC̨̦̺̩̲̥͉̭͚̜̻̝̣̼͙̮̯̪o̴̡͇̘͎̞̲͇̦̲͞͡m̸̩̺̝̣̹̱͚̬̥̫̳̼̞̘̯͘ͅe" +
          "̣͇̺̜́̕͢s̶̙̟̱̥̮̯̰̦͓͇͖͖̝͘͘͞"
      ),
      new BuildingUpgradeWithAchievement(
        "Embedded microportals", new BuildingRequirement(
          Building
            .portal, 400
        ), "What happens in the vortex stays in the vortex"
      ),
      new BuildingUpgradeWithAchievement(
        "His advent", new BuildingRequirement(
          Building
            .portal, 450
        ), "Objects in the mirror dimension are closer than they appear"
      ),
      new BuildingUpgradeWithAchievement(
        "Domestic rifts", new BuildingRequirement(
          Building
            .portal, 500
        ), "Your brain gets smart but your head gets dumb"
      ),
      //endregion,
      //region Time machine,
      new BuildingUpgradeWithAchievement(
        "Flux capacitors", new BuildingRequirement(
          Building
            .timeMachine, 1
        ), "Time warp"
      ),
      new BuildingUpgrade("Time paradox resolver", new BuildingRequirement(Building.timeMachine, 5)),
      new BuildingUpgrade("Quantum conundrum", new BuildingRequirement(Building.timeMachine, 25)),
      new BuildingUpgradeWithAchievement(
        "Causality enforcer", new BuildingRequirement(
          Building
            .timeMachine, 50
        ), "Alternate timeline"
      ),
      new BuildingUpgradeWithAchievement(
        "Yestermorrow comparators", new BuildingRequirement(
          Building
            .timeMachine, 100
        ), "Rewriting history"
      ),
      new BuildingUpgradeWithAchievement(
        "Far future enactment", new BuildingRequirement(
          Building
            .timeMachine, 150
        ), "Time duke"
      ),
      new BuildingUpgradeWithAchievement(
        "Great loop hypothesis", new BuildingRequirement(
          Building
            .timeMachine, 200
        ), "Forever and ever"
      ),
      new BuildingUpgradeWithAchievement(
        "Cookietopian moments of maybe", new BuildingRequirement(
          Building
            .timeMachine, 250
        ), "Heat death"
      ),
      new BuildingUpgradeWithAchievement(
        "Second seconds", new BuildingRequirement(
          Building
            .timeMachine, 300
        ), "cookie clicker forever and forever a hundred years cookie clicker, all day long forever, forever a " +
          "hundred times, over and over cookie clicker adventures dot com"
      ),
      new BuildingUpgradeWithAchievement(
        "Additional clock hands", new BuildingRequirement(
          Building
            .timeMachine, 350
        ), "Way back then"
      ),
      new BuildingUpgradeWithAchievement(
        "Nostalgia", new BuildingRequirement(
          Building
            .timeMachine, 400
        ), "Invited to yesterday's party"
      ),
      new BuildingUpgradeWithAchievement(
        "Split seconds", new BuildingRequirement(
          Building
            .timeMachine, 450
        ), "Groundhog day"
      ),
      new BuildingUpgradeWithAchievement(
        "Patience abolished", new BuildingRequirement(
          Building
            .timeMachine, 500
        ), "The years start coming"
      ),
      //endregion,
      //region Antimatter condensers,
      new BuildingUpgradeWithAchievement(
        "Sugar bosons", new BuildingRequirement(
          Building
            .antimatterCondenser, 1
        ), "Antibatter"
      ),
      new BuildingUpgrade("String theory", new BuildingRequirement(Building.antimatterCondenser, 5)),
      new BuildingUpgrade("Large macaron collider", new BuildingRequirement(Building.antimatterCondenser, 25)),
      new BuildingUpgradeWithAchievement(
        "Big bang bake", new BuildingRequirement(
          Building
            .antimatterCondenser, 50
        ), "Quirky quarks"
      ),
      new BuildingUpgradeWithAchievement(
        "Reverse cyclotrons", new BuildingRequirement(
          Building
            .antimatterCondenser, 100
        ), "It does matter!"
      ),
      new BuildingUpgradeWithAchievement(
        "Nanocosmics", new BuildingRequirement(
          Building
            .antimatterCondenser, 150
        ), "Molecular maestro"
      ),
      new BuildingUpgradeWithAchievement(
        "The Pulse", new BuildingRequirement(
          Building
            .antimatterCondenser, 200
        ), "Walk the planck"
      ),
      new BuildingUpgradeWithAchievement(
        "Some other super-tiny fundamental particle? Probably?", new BuildingRequirement(
          Building
            .antimatterCondenser, 250
        ), "Microcosm"
      ),
      new BuildingUpgradeWithAchievement(
        "Quantum comb", new BuildingRequirement(
          Building
            .antimatterCondenser, 300
        ), "Scientists baffled everywhere"
      ),
      new BuildingUpgradeWithAchievement(
        "Baking Nobel prize", new BuildingRequirement(
          Building
            .antimatterCondenser, 350
        ), "Exotic matter"
      ),
      new BuildingUpgradeWithAchievement(
        "The definite molecule", new BuildingRequirement(
          Building
            .antimatterCondenser, 400
        ), "Downsizing"
      ),
      new BuildingUpgradeWithAchievement(
        "Flavor itself", new BuildingRequirement(
          Building
            .antimatterCondenser, 450
        ), "A matter of perspective"
      ),
      new BuildingUpgradeWithAchievement(
        "Delicious pull", new BuildingRequirement(
          Building
            .antimatterCondenser, 500
        ), "What a concept"
      ),
      //endregion,
      //region Prisms
      new BuildingUpgradeWithAchievement("Gem polish", new BuildingRequirement(Building.prism, 1), "Lone photon"),
      new BuildingUpgrade("9th color", new BuildingRequirement(Building.prism, 5)),
      new BuildingUpgrade("Chocolate light", new BuildingRequirement(Building.prism, 25)),
      new BuildingUpgradeWithAchievement("Grainbow", new BuildingRequirement(Building.prism, 50), "Dazzling glimmer"),
      new BuildingUpgradeWithAchievement(
        "Pure cosmic light", new BuildingRequirement(
          Building
            .prism, 100
        ), "Blinding flash"
      ),
      new BuildingUpgradeWithAchievement(
        "Glow-in-the-dark", new BuildingRequirement(
          Building
            .prism, 150
        ), "Unending glow"
      ),
      new BuildingUpgradeWithAchievement(
        "Lux sanctorum", new BuildingRequirement(
          Building
            .prism, 200
        ), "Rise and shine"
      ),
      new BuildingUpgradeWithAchievement(
        "Reverse shadows", new BuildingRequirement(
          Building
            .prism, 250
        ), "Bright future"
      ),
      new BuildingUpgradeWithAchievement(
        "Crystal mirrors", new BuildingRequirement(
          Building
            .prism, 300
        ), "Harmony of the spheres"
      ),
      new BuildingUpgradeWithAchievement(
        "Reverse theory of light", new BuildingRequirement(
          Building
            .prism, 350
        ), "At the end of the tunnel"
      ),
      new BuildingUpgradeWithAchievement(
        "Light capture measures", new BuildingRequirement(
          Building
            .prism, 400
        ), "My eyes"
      ),
      new BuildingUpgradeWithAchievement(
        "Light speed limit", new BuildingRequirement(
          Building
            .prism, 450
        ), "Optical illusion"
      ),
      new BuildingUpgradeWithAchievement(
        "Occam's laser", new BuildingRequirement(
          Building
            .prism, 500
        ), "You'll never shine if you don't glow"
      ),
      //endregion,
      //region Chancemaker
      new BuildingUpgradeWithAchievement(
        "Your lucky cookie", new BuildingRequirement(
          Building
            .chancemaker, 1
        ), "Lucked out"
      ),
      new BuildingUpgrade("\"All Bets Are Off\" magic coin", new BuildingRequirement(Building.chancemaker, 5)),
      new BuildingUpgrade("Winning lottery ticket", new BuildingRequirement(Building.chancemaker, 25)),
      new BuildingUpgradeWithAchievement(
        "Four-leaf clover field", new BuildingRequirement(
          Building
            .chancemaker, 50
        ), "What are the odds"
      ),
      new BuildingUpgradeWithAchievement(
        "A recipe book about books", new BuildingRequirement(
          Building
            .chancemaker, 100
        ), "Grandma needs a new pair of shoes"
      ),
      new BuildingUpgradeWithAchievement(
        "Leprechaun village", new BuildingRequirement(
          Building
            .chancemaker, 150
        ), "Million to one shot, doc"
      ),
      new BuildingUpgradeWithAchievement(
        "Improbability drive", new BuildingRequirement(
          Building
            .chancemaker, 200
        ), "As luck would have it"
      ),
      new BuildingUpgradeWithAchievement(
        "Antisuperstistronics", new BuildingRequirement(
          Building
            .chancemaker, 250
        ), "Ever in your favor"
      ),
      new BuildingUpgradeWithAchievement("Bunnypedes", new BuildingRequirement(Building.chancemaker, 300), "Be a lady"),
      new BuildingUpgradeWithAchievement(
        "Revised probabilistics", new BuildingRequirement(
          Building
            .chancemaker, 350
        ), "Dicey business"
      ),
      new BuildingUpgradeWithAchievement(
        "0-sided dice", new BuildingRequirement(
          Building
            .chancemaker, 400
        ), "Maybe a chance in hell, actually"
      ),
      new BuildingUpgradeWithAchievement(
        "A touch of determinism", new BuildingRequirement(
          Building
            .chancemaker, 450
        ), "Jackpot"
      ),
      new BuildingUpgradeWithAchievement(
        "On a streak", new BuildingRequirement(
          Building
            .chancemaker, 500
        ), "You'll never know if you don't go"
      ),
      //endregion,
      //region Fractal Engine,
      new BuildingUpgradeWithAchievement(
        "Metabakeries", new BuildingRequirement(
          Building
            .fractalEngine, 1
        ), "Self-contained"
      ),
      new BuildingUpgrade("Mandelbrown sugar", new BuildingRequirement(Building.fractalEngine, 5)),
      new BuildingUpgrade("Fractoids", new BuildingRequirement(Building.fractalEngine, 25)),
      new BuildingUpgradeWithAchievement(
        "Nested universe theory", new BuildingRequirement(
          Building
            .fractalEngine, 50
        ), "Threw you for a loop"
      ),
      new BuildingUpgradeWithAchievement(
        "Menger sponge cake", new BuildingRequirement(
          Building
            .fractalEngine, 100
        ), "The sum of its parts"
      ),
      new BuildingUpgradeWithAchievement(
        "One particularly good-humored cow", new BuildingRequirement(
          Building
            .fractalEngine, 150
        ), "Bears repeating"
      ),
      new BuildingUpgradeWithAchievement(
        "Chocolate ouroboros", new BuildingRequirement(
          Building
            .fractalEngine, 200
        ), "More of the same"
      ),
      new BuildingUpgradeWithAchievement(
        "Nested", new BuildingRequirement(
          Building
            .fractalEngine, 250
        ), "Last recurse"
      ),
      new BuildingUpgradeWithAchievement(
        "Space-filling fibers", new BuildingRequirement(
          Building
            .fractalEngine, 300
        ), "Out of one, many"
      ),
      new BuildingUpgradeWithAchievement(
        "Endless book of prose", new BuildingRequirement(
          Building
            .fractalEngine, 350
        ), "An example of recursion"
      ),
      new BuildingUpgradeWithAchievement(
        "The set of all sets", new BuildingRequirement(
          Building
            .fractalEngine, 400
        ), "For more information on this achievement, please refer to its title"
      ),
      new BuildingUpgradeWithAchievement(
        "This upgrade", new BuildingRequirement(
          Building
            .fractalEngine, 450
        ), "I'm so meta, even this achievement"
      ),
      new BuildingUpgradeWithAchievement(
        "A box", new BuildingRequirement(
          Building
            .fractalEngine, 500
        ), "Never get bored"
      ),
      //endregion,
      //TODO: Heralds Upgrade,
      //region Heavenly Chips Power,
      new HeavenlyChipUpgrade("Heavenly chip secret", 5),
      new HeavenlyChipUpgradeWithSingleUpgradeRequired("Heavenly cookie stand", 20, "Heavenly chip secret"),
      new HeavenlyChipUpgradeWithSingleUpgradeRequired("Heavenly bakery", 25, "Heavenly cookie stand"),
      new HeavenlyChipUpgradeWithSingleUpgradeRequired("Heavenly confectionery", 25, "Heavenly bakery"),
      new HeavenlyChipUpgradeWithSingleUpgradeRequired("Heavenly key", 25, "Heavenly confectionery"),
      //endregion,
      //region Research/Bingo Center,
      new ResearchUpgrade("Bingo center/Research facility"),
      new ResearchCookieUpgrade("Specialized chocolate chips", "Bingo center/Research facility", 1D),
      new ResearchCookieUpgrade("Designer cocoa beans", "Specialized chocolate chips", 2D),
      new ResearchUpgrade("Ritual rolling pins", "Designer cocoa beans"),
      new ResearchCookieUpgrade("Underworld ovens", "Ritual rolling pins", 3D),
      new ResearchUpgrade("One mind", "Underworld ovens"),
      new ResearchCookieUpgrade("Exotic nuts", "One mind", 4D),
      new ResearchUpgrade("Communal brainsweep", "Exotic nuts"),
      new ResearchCookieUpgrade("Arcane sugar", "Communal brainsweep", 5D),
      new ResearchUpgrade("Elder Pact", "Arcane sugar"),
      //endregion,
      //region Kitten
      new KittenUpgrade("Kitten helpers", 0.52),
      new KittenUpgrade("Kitten workers", 1),
      new KittenUpgrade("Kitten engineers", 2),
      new KittenUpgrade("Kitten overseers", 3),
      new KittenUpgrade("Kitten managers", 4),
      new KittenUpgrade("Kitten accountants", 5),
      new KittenUpgrade("Kitten specialists", 6),
      new KittenUpgrade("Kitten experts", 7),
      new KittenUpgrade("Kitten consultants", 8),
      new KittenUpgrade("Kitten assistants to the regional manager", 9),
      new KittenUpgrade("Kitten marketeers", 10),
      new KittenUpgrade("Kitten analysts", 11),
      //endregion,
      //region Mouse
      new MouseUpgrade("Plastic mouse"),
      new MouseUpgrade("Iron mouse"),
      new MouseUpgrade("Titanium mouse"),
      new MouseUpgrade("Adamantium mouse"),
      new MouseUpgrade("Unobtainium mouse"),
      new MouseUpgrade("Eludium mouse"),
      new MouseUpgrade("Wishalloy mouse"),
      new MouseUpgrade("Fantasteel mouse"),
      new MouseUpgrade("Nevercrack mouse"),
      new MouseUpgrade("Armythril mouse"),
      new MouseUpgrade("Technobsidian mouse"),
      new MouseUpgrade("Plasmarble mouse"),
      //endregion,
      //region Golden Cookie,
      new GoldenCookieUpgrade("Lucky day"),
      new GoldenCookieUpgrade("Serendipity"),
      new GoldenCookieUpgrade("Get lucky"),
      new GoldenCookieUpgrade("Golden goose egg"),
      new GoldenCookieUpgrade("Green yeast digestives"),
      //endregion,
      //region Cookies
      new CookieUpgrade("Plain cookies"),
      new CookieUpgrade("Sugar cookies"),
      new CookieUpgrade("Oatmeal raisin cookies"),
      new CookieUpgrade("Peanut butter cookies"),
      new CookieUpgrade("Coconut cookies"),
      new CookieUpgrade("Almond cookies"),
      new CookieUpgrade("Hazelnut cookies"),
      new CookieUpgrade("Walnut cookies"),
      new CookieUpgrade("Cashew cookies"),
      new CookieUpgrade("White chocolate cookies"),
      new CookieUpgrade("Milk chocolate cookies"),
      new CookieUpgrade("Macadamia nut cookies"),
      new CookieUpgrade("Double-chip cookies"),
      new CookieUpgrade("White chocolate macadamia nut cookies"),
      new CookieUpgrade("All-chocolate cookies"),
      new CookieUpgrade("Dark chocolate-coated cookies"),
      new CookieUpgrade("White chocolate-coated cookies"),
      new CookieUpgrade("Eclipse cookies"),
      new CookieUpgrade("Zebra cookies"),
      new CookieUpgrade("Snickerdoodles"),
      new CookieUpgrade("Stroopwafels"),
      new CookieUpgrade("Macaroons"),
      new CookieUpgrade("Madeleines"),
      new CookieUpgrade("Palmiers"),
      new CookieUpgrade("Palets"),
      new CookieUpgrade("Sabl&eacute;s"),

      new CookieUpgradeWithSingleUpgradeRequired("Caramoas", "Box of brand biscuits"),
      new CookieUpgradeWithSingleUpgradeRequired("Sagalongs", "Box of brand biscuits"),
      new CookieUpgradeWithSingleUpgradeRequired("Shortfoils", "Box of brand biscuits"),
      new CookieUpgradeWithSingleUpgradeRequired("Win mints", "Box of brand biscuits"),
      new CookieUpgradeWithSingleUpgradeRequired("Fig gluttons", "Box of brand biscuits"),
      new CookieUpgradeWithSingleUpgradeRequired("Loreols", "Box of brand biscuits"),
      new CookieUpgradeWithSingleUpgradeRequired("Jaffa cakes", "Box of brand biscuits"),
      new CookieUpgradeWithSingleUpgradeRequired("Grease's cups", "Box of brand biscuits"),
      new CookieUpgradeWithSingleUpgradeRequired("Digits", "Box of brand biscuits"),
      new CookieUpgradeWithSingleUpgradeRequired("Lombardia cookies", "Box of brand biscuits"),
      new CookieUpgradeWithSingleUpgradeRequired("Bastenaken cookies", "Box of brand biscuits"),
      new CookieUpgradeWithSingleUpgradeRequired("Festivity loops", "Box of brand biscuits"),
      new CookieUpgradeWithSingleUpgradeRequired("Havabreaks", "Box of brand biscuits"),
      new CookieUpgradeWithSingleUpgradeRequired("Zilla wafers", "Box of brand biscuits"),
      new CookieUpgradeWithSingleUpgradeRequired("Dim Dams", "Box of brand biscuits"),
      new CookieUpgradeWithSingleUpgradeRequired("Pokey", "Box of brand biscuits"),

      new CookieUpgrade("Gingerbread men"),
      new CookieUpgrade("Gingerbread trees"),
      new CookieUpgrade("Pure black chocolate cookies"),
      new CookieUpgrade("Pure white chocolate cookies"),
      new CookieUpgrade("Ladyfingers"),
      new CookieUpgrade("Tuiles"),
      new CookieUpgrade("Chocolate-stuffed biscuits"),
      new CookieUpgrade("Checker cookies"),
      new CookieUpgrade("Butter cookies"),
      new CookieUpgrade("Cream cookies"),
      new CookieUpgrade("Gingersnaps"),
      new CookieUpgrade("Cinnamon cookies"),
      new CookieUpgrade("Vanity cookies"),
      new CookieUpgrade("Cigars"),
      new CookieUpgrade("Pinwheel cookies"),
      new CookieUpgrade("Fudge squares"),
      new CookieUpgrade("Shortbread biscuits"),
      new CookieUpgrade("Millionaires' shortbreads"),
      new CookieUpgrade("Caramel cookies"),
      new CookieUpgrade("Pecan sandies"),
      new CookieUpgrade("Moravian spice cookies"),
      new CookieUpgrade("Anzac biscuits"),
      new CookieUpgrade("Buttercakes"),
      new CookieUpgrade("Ice cream sandwiches"),
      new CookieUpgrade("Birthday cookie"),
      new CookieUpgrade("Pink biscuits"),
      new CookieUpgrade("Whole-grain cookies"),
      new CookieUpgrade("Candy cookies"),
      new CookieUpgrade("Big chip cookies"),
      new CookieUpgrade("One chip cookies"),
      new CookieUpgrade("Sprinkles cookies"),
      new CookieUpgrade("Peanut butter blossoms"),
      new CookieUpgrade("No-bake cookies"),
      new CookieUpgrade("Florentines"),
      new CookieUpgrade("Chocolate crinkles"),
      new CookieUpgrade("Maple cookies"),
      new CookieUpgrade("Persian rice cookies"),
      new CookieUpgrade("Norwegian cookies"),
      new CookieUpgrade("Crispy rice cookies"),
      new CookieUpgrade("Ube cookies"),
      new CookieUpgrade("Butterscotch cookies"),
      new CookieUpgrade("Speculaas"),
      new CookieUpgrade("Chocolate oatmeal cookies"),
      new CookieUpgrade("Molasses cookies"),
      new CookieUpgrade("Biscotti"),
      new CookieUpgrade("Waffle cookies"),
      new CookieUpgrade("Custard creams"),
      new CookieUpgrade("Bourbon biscuits"),
      new CookieUpgrade("Mini-cookies"),
      new CookieUpgrade("Whoopie pies"),
      new CookieUpgrade("Caramel wafer biscuits"),
      new CookieUpgrade("Chocolate chip mocha cookies"),
      new CookieUpgrade("Earl Grey cookies"),
      new CookieUpgrade("Chai tea cookies"),
      new CookieUpgrade("Corn syrup cookies"),
      new CookieUpgrade("Icebox cookies"),
      new CookieUpgrade("Graham crackers"),
      new CookieUpgrade("Hardtack"),
      new CookieUpgrade("Cornflake cookies"),
      new CookieUpgrade("Tofu cookies"),
      new CookieUpgrade("Gluten-free cookies"),
      new CookieUpgrade("Russian bread cookies"),
      new CookieUpgrade("Lebkuchen"),
      new CookieUpgrade("Aachener Printen"),
      new CookieUpgrade("Canistrelli"),
      new CookieUpgrade("Nice biscuits"),
      new CookieUpgrade("French pure butter cookies"),
      new CookieUpgrade("Petit beurre"),
      new CookieUpgrade("Nanaimo bars"),
      new CookieUpgrade("Berger cookies"),
      new CookieUpgrade("Chinsuko"),
      new CookieUpgrade("Panda koala biscuits"),
      new CookieUpgrade("Putri salju"),
      new CookieUpgrade("Milk cookies"),
      new CookieUpgrade("Kruidnoten"),
      new CookieUpgrade("Marie biscuits"),
      new CookieUpgrade("Meringue cookies"),
      new CookieUpgrade("Yogurt cookies"),
      new CookieUpgrade("Thumbprint cookies"),
      new CookieUpgrade("Pizzelle"),
      new CookieUpgrade("Granola cookies"),
      new CookieUpgrade("Ricotta cookies"),
      new CookieUpgrade("Roze koeken"),
      new CookieUpgrade("Peanut butter cup cookies"),
      new CookieUpgrade("Sesame cookies"),
      new CookieUpgrade("Taiyaki"),
      new CookieUpgrade("Vanillekipferl"),
      new CookieUpgrade("Battenberg biscuits"),
      new CookieUpgrade("Rosette cookies"),
      new CookieUpgrade("Gangmakers"),
      new CookieUpgrade("Welsh cookies"),
      new CookieUpgrade("Raspberry cheesecake cookies"),
      new CookieUpgrade("Bokkenpootjes"),
      new CookieUpgrade("Fat rascals"),
      new CookieUpgrade("Ischler cookies"),
      new CookieUpgrade("Matcha cookies"),

      new CookieUpgrade("Empire biscuits"),
      new CookieUpgradeWithSingleUpgradeRequired("British tea biscuits", "Tin of british tea biscuits"),
      new CookieUpgradeWithSingleUpgradeRequired("Chocolate british tea biscuits", "British tea biscuits"),
      new CookieUpgradeWithSingleUpgradeRequired("Round british tea biscuits", "Chocolate british tea biscuits"),
      new CookieUpgradeWithSingleUpgradeRequired("Round chocolate british tea biscuits", "Round british tea biscuits"),
      new CookieUpgradeWithSingleUpgradeRequired("Round british tea biscuits with heart motif", "Round chocolate " +
        "british tea biscuits"),
      new CookieUpgradeWithSingleUpgradeRequired("Round chocolate british tea biscuits with heart motif", "Round " +
        "british tea biscuits with heart motif"),

      new CookieUpgradeWithSingleUpgradeRequired("Butter horseshoes", "Tin of butter cookies"),
      new CookieUpgradeWithSingleUpgradeRequired("Butter pucks", "Tin of butter cookies"),
      new CookieUpgradeWithSingleUpgradeRequired("Butter knots", "Tin of butter cookies"),
      new CookieUpgradeWithSingleUpgradeRequired("Butter slabs", "Tin of butter cookies"),
      new CookieUpgradeWithSingleUpgradeRequired("Butter swirls", "Tin of butter cookies"),

      new CookieUpgradeWithSingleUpgradeRequired("Rose macarons", "Box of macarons"),
      new CookieUpgradeWithSingleUpgradeRequired("Lemon macarons", "Box of macarons"),
      new CookieUpgradeWithSingleUpgradeRequired("Chocolate macarons", "Box of macarons"),
      new CookieUpgradeWithSingleUpgradeRequired("Pistachio macarons", "Box of macarons"),
      new CookieUpgradeWithSingleUpgradeRequired("Hazelnut macarons", "Box of macarons"),
      new CookieUpgradeWithSingleUpgradeRequired("Violet macarons", "Box of macarons"),
      new CookieUpgradeWithSingleUpgradeRequired("Caramel macarons", "Box of macarons"),
      new CookieUpgradeWithSingleUpgradeRequired("Licorice macarons", "Box of macarons"),
      new CookieUpgradeWithSingleUpgradeRequired("Earl Grey macarons", "Box of macarons"),

      new CookieUpgradeWithSingleUpgradeRequired("Cookie dough", "Box of maybe cookies"),
      new CookieUpgradeWithSingleUpgradeRequired("Burnt cookie", "Box of maybe cookies"),
      new CookieUpgradeWithSingleUpgradeRequired("A chocolate chip cookie but with the chips picked off for some " +
        "reason", "Box of maybe cookies"),
      new CookieUpgradeWithSingleUpgradeRequired("Flavor text cookie", "Box of maybe cookies"),
      new CookieUpgradeWithSingleUpgradeRequired("High-definition cookie", "Box of maybe cookies"),
      new CookieUpgradeWithSingleUpgradeRequired("Crackers", "Box of maybe cookies"),

      new CookieUpgradeWithSingleUpgradeRequired("Toast", "Box of not cookies"),
      new CookieUpgradeWithSingleUpgradeRequired("Peanut butter & jelly", "Box of not cookies"),
      new CookieUpgradeWithSingleUpgradeRequired("Wookies", "Box of not cookies"),
      new CookieUpgradeWithSingleUpgradeRequired("Cheeseburger", "Box of not cookies"),
      new CookieUpgradeWithSingleUpgradeRequired("One lone chocolate chip", "Box of not cookies"),
      new CookieUpgradeWithSingleUpgradeRequired("Pizza", "Box of not cookies"),
      new CookieUpgradeWithSingleUpgradeRequired("Candy", "Box of not cookies"),

      new CookieUpgradeWithSingleUpgradeRequired("Profiteroles", "Box of pastries"),
      new CookieUpgradeWithSingleUpgradeRequired("Jelly donut", "Box of pastries"),
      new CookieUpgradeWithSingleUpgradeRequired("Glazed donut", "Box of pastries"),
      new CookieUpgradeWithSingleUpgradeRequired("Chocolate cake", "Box of pastries"),
      new CookieUpgradeWithSingleUpgradeRequired("Strawberry cake", "Box of pastries"),
      new CookieUpgradeWithSingleUpgradeRequired("Apple pie", "Box of pastries"),
      new CookieUpgradeWithSingleUpgradeRequired("Lemon meringue pie", "Box of pastries"),
      new CookieUpgradeWithSingleUpgradeRequired("Butter croissant", "Box of pastries"),

      new CookieUpgradeWithSingleUpgradeRequired("Cookie crumbs", "Legacy"),
      new CookieUpgradeWithSingleUpgradeRequired("Chocolate chip cookie", "Legacy"),

      new CookieUpgrade("Milk chocolate butter biscuit", BuildingRequirement.generateRequirementsForAllBuildings(100)),
      new CookieUpgrade("Dark chocolate butter biscuit", BuildingRequirement.generateRequirementsForAllBuildings(150)),
      new CookieUpgrade("White chocolate butter biscuit", BuildingRequirement.generateRequirementsForAllBuildings(200)),
      new CookieUpgrade("Ruby chocolate butter biscuit", BuildingRequirement.generateRequirementsForAllBuildings(250)),
      new CookieUpgrade("Lavender chocolate butter biscuit", BuildingRequirement.generateRequirementsForAllBuildings
      (300)),
      new CookieUpgrade("Synthetic chocolate green honey butter biscuit", BuildingRequirement
        .generateRequirementsForAllBuildings(350)),
      new CookieUpgrade("Royal raspberry chocolate butter biscuit", BuildingRequirement
        .generateRequirementsForAllBuildings(400)),
      new CookieUpgrade("Ultra-concentrated high-energy chocolate butter biscuit", BuildingRequirement
        .generateRequirementsForAllBuildings(450)),
      new CookieUpgrade("Pure pitch-black chocolate butter biscuit", BuildingRequirement
        .generateRequirementsForAllBuildings(500)),
      new CookieUpgrade("Cosmic chocolate butter biscuit", BuildingRequirement.generateRequirementsForAllBuildings
      (550)),
      new CookieUpgrade("Butter biscuit (with butter)", BuildingRequirement.generateRequirementsForAllBuildings(600)),
      //endregion,
      //region Synergies
      new BuildingUpgradeWithSingleUpgradeRequired(
        "Extra physics funding", "Synergies Vol. I", new BuildingRequirement(
          Building
            .bank, 15
        ), new BuildingRequirement(Building.antimatterCondenser, 15)
      ),
      new BuildingUpgradeWithSingleUpgradeRequired(
        "Contracts from beyond", "Synergies Vol. I", new BuildingRequirement(
          Building
            .bank, 15
        ), new BuildingRequirement(Building.prism, 15)
      ),
      new BuildingUpgradeWithSingleUpgradeRequired(
        "Quantum electronics", "Synergies Vol. I", new BuildingRequirement(
          Building
            .factory, 15
        ), new BuildingRequirement(Building.antimatterCondenser, 15)
      ),
      new BuildingUpgradeWithSingleUpgradeRequired(
        "Infernal crops", "Synergies Vol. I", new BuildingRequirement(
          Building
            .farm, 15
        ), new BuildingRequirement(Building.portal, 15)
      ),
      new BuildingUpgradeWithSingleUpgradeRequired(
        "Future almanacs", "Synergies Vol. I", new BuildingRequirement(
          Building
            .farm, 15
        ), new BuildingRequirement(Building.timeMachine, 15)
      ),
      new BuildingUpgradeWithSingleUpgradeRequired(
        "Primordial ores", "Synergies Vol. I", new BuildingRequirement(
          Building
            .mine, 15
        ), new BuildingRequirement(Building.alchemyLab, 15)
      ),
      new BuildingUpgradeWithSingleUpgradeRequired(
        "Gemmed talismans", "Synergies Vol. I", new BuildingRequirement(
          Building
            .mine, 15
        ), new BuildingRequirement(Building.chancemaker, 15)
      ),
      new BuildingUpgradeWithSingleUpgradeRequired(
        "Fossil fuels", "Synergies Vol. I", new BuildingRequirement(
          Building
            .mine, 15
        ), new BuildingRequirement(Building.shipment, 15)
      ),
      new BuildingUpgradeWithSingleUpgradeRequired(
        "Seismic magic", "Synergies Vol. I", new BuildingRequirement(
          Building
            .mine, 15
        ), new BuildingRequirement(Building.wizardTower, 15)
      ),
      new BuildingUpgradeWithSingleUpgradeRequired(
        "Recursive mirrors", "Synergies Vol. I", new BuildingRequirement(
          Building
            .prism, 15
        ), new BuildingRequirement(Building.fractalEngine, 15)
      ),
      new BuildingUpgradeWithSingleUpgradeRequired(
        "Relativistic parsec-skipping", "Synergies Vol. I", new BuildingRequirement(
          Building
            .shipment, 15
        ), new BuildingRequirement(Building.timeMachine, 15)
      ),
      new BuildingUpgradeWithSingleUpgradeRequired(
        "Paganism", "Synergies Vol. I", new BuildingRequirement(
          Building
            .temple, 15
        ), new BuildingRequirement(Building.portal, 15)
      ),
      new BuildingUpgradeWithSingleUpgradeRequired(
        "Arcane knowledge", "Synergies Vol. I", new BuildingRequirement(
          Building
            .wizardTower, 15
        ), new BuildingRequirement(Building.alchemyLab, 15)
      ),
      new BuildingUpgradeWithSingleUpgradeRequired(
        "Light magic", "Synergies Vol. I", new BuildingRequirement(
          Building
            .wizardTower, 15
        ), new BuildingRequirement(Building.prism, 15)
      ),


      new BuildingUpgradeWithSingleUpgradeRequired(
        "Chemical proficiency", "Synergies Vol. II", new BuildingRequirement(
          Building
            .alchemyLab, 75
        ), new BuildingRequirement(Building.antimatterCondenser, 75)
      ),
      new BuildingUpgradeWithSingleUpgradeRequired(
        "Charm quarks", "Synergies Vol. II", new BuildingRequirement(
          Building
            .antimatterCondenser, 75
        ), new BuildingRequirement(Building.chancemaker, 75)
      ),
      new BuildingUpgradeWithSingleUpgradeRequired(
        "Gold fund", "Synergies Vol. II", new BuildingRequirement(
          Building
            .bank, 75
        ), new BuildingRequirement(Building.alchemyLab, 75)
      ),
      new BuildingUpgradeWithSingleUpgradeRequired(
        "Printing presses", "Synergies Vol. II", new BuildingRequirement(
          Building
            .bank, 75
        ), new BuildingRequirement(Building.factory, 75)
      ),
      new BuildingUpgradeWithSingleUpgradeRequired(
        "Mice clicking mice", "Synergies Vol. II", new BuildingRequirement(
          Building
            .cursor, 75
        ), new BuildingRequirement(Building.fractalEngine, 75)
      ),
      new BuildingUpgradeWithSingleUpgradeRequired(
        "Shipyards", "Synergies Vol. II", new BuildingRequirement(
          Building
            .factory, 75
        ), new BuildingRequirement(Building.shipment, 75)
      ),
      new BuildingUpgradeWithSingleUpgradeRequired(
        "Temporal overclocking", "Synergies Vol. II", new BuildingRequirement(
          Building
            .factory, 75
        ), new BuildingRequirement(Building.timeMachine, 75)
      ),
      new BuildingUpgradeWithSingleUpgradeRequired(
        "Rain prayer", "Synergies Vol. II", new BuildingRequirement(
          Building
            .farm, 75
        ), new BuildingRequirement(Building.temple, 75)
      ),
      new BuildingUpgradeWithSingleUpgradeRequired(
        "Asteroid mining", "Synergies Vol. II", new BuildingRequirement(
          Building
            .mine, 75
        ), new BuildingRequirement(Building.shipment, 75)
      ),
      new BuildingUpgradeWithSingleUpgradeRequired(
        "Abysmal glimmer", "Synergies Vol. II", new BuildingRequirement(
          Building
            .prism, 75
        ), new BuildingRequirement(Building.portal, 75)
      ),
      new BuildingUpgradeWithSingleUpgradeRequired(
        "Primeval glow", "Synergies Vol. II", new BuildingRequirement(
          Building
            .prism, 75
        ), new BuildingRequirement(Building.timeMachine, 75)
      ),
      new BuildingUpgradeWithSingleUpgradeRequired(
        "God particle", "Synergies Vol. II", new BuildingRequirement(
          Building
            .temple, 75
        ), new BuildingRequirement(Building.antimatterCondenser, 75)
      ),
      new BuildingUpgradeWithSingleUpgradeRequired(
        "Mystical energies", "Synergies Vol. II", new BuildingRequirement(
          Building
            .temple, 75
        ), new BuildingRequirement(Building.prism, 75)
      ),
      new BuildingUpgradeWithSingleUpgradeRequired(
        "Magical botany", "Synergies Vol. II", new BuildingRequirement(
          Building
            .wizardTower, 75
        ), new BuildingRequirement(Building.farm, 75)
      ),
      //endregion
    ).map(upgrade => upgrade.name -> upgrade).toMap

  def createAchievements: Map[String, Achievement] =
    Seq(
      //region Cursor
      new Achievement("Click", new BuildingRequirement(Building.cursor, 1)),
      new Achievement("Double-click", new BuildingRequirement(Building.cursor, 2)),
      new Achievement("Mouse wheel", new BuildingRequirement(Building.cursor, 50)),
      new Achievement("Of Mice and Men", new BuildingRequirement(Building.cursor, 100)),
      new Achievement("The Digital", new BuildingRequirement(Building.cursor, 200)),
      new Achievement("Extreme polydactyly", new BuildingRequirement(Building.cursor, 300)),
      new Achievement("Dr. T", new BuildingRequirement(Building.cursor, 400)),
      new Achievement("Thumbs, phalanges, metacarpals", new BuildingRequirement(Building.cursor, 500)),
      new Achievement("With her finger and her thumb", new BuildingRequirement(Building.cursor, 600)),
      new Achievement("Gotta hand it to you", new BuildingRequirement(Building.cursor, 700)),
      new Achievement("The devil's workshop", new BuildingRequirement(Building.cursor, 800)),
      //endregion
      //region Grandma
      new Achievement("Grandma\'s cookies", new BuildingRequirement(Building.grandma, 1)),
      new Achievement("Sloppy kisses", new BuildingRequirement(Building.grandma, 50)),
      new Achievement("Retirement home", new BuildingRequirement(Building.grandma, 100)),
      new Achievement("Friend of the ancients", new BuildingRequirement(Building.grandma, 150)),
      new Achievement("Ruler of the ancients", new BuildingRequirement(Building.grandma, 200)),
      new Achievement("The old never bothered me anyway", new BuildingRequirement(Building.grandma, 250)),
      new Achievement("The agemaster", new BuildingRequirement(Building.grandma, 300)),
      new Achievement("To oldly go", new BuildingRequirement(Building.grandma, 350)),
      new Achievement("Aged well", new BuildingRequirement(Building.grandma, 400)),
      new Achievement("101st birthday", new BuildingRequirement(Building.grandma, 450)),
      new Achievement("Defense of the ancients", new BuildingRequirement(Building.grandma, 500)),
      new Achievement("But wait 'til you get older", new BuildingRequirement(Building.grandma, 550)),
      new Achievement("Okay boomer", new BuildingRequirement(Building.grandma, 600)),
      new Achievement("Elder"),
      //endregion
      //region Farm
      new Achievement("Bought the farm", new BuildingRequirement(Building.farm, 1)),
      new Achievement("Reap what you sow", new BuildingRequirement(Building.farm, 50)),
      new Achievement("Farm ill", new BuildingRequirement(Building.farm, 100)),
      new Achievement("Perfected agriculture", new BuildingRequirement(Building.farm, 150)),
      new Achievement("Homegrown", new BuildingRequirement(Building.farm, 200)),
      new Achievement("Gardener extraordinaire", new BuildingRequirement(Building.farm, 250)),
      new Achievement("Seedy business", new BuildingRequirement(Building.farm, 300)),
      new Achievement("You and the beanstalk", new BuildingRequirement(Building.farm, 350)),
      new Achievement("Harvest moon", new BuildingRequirement(Building.farm, 400)),
      new Achievement("Make like a tree", new BuildingRequirement(Building.farm, 450)),
      new Achievement("Sharpest tool in the shed", new BuildingRequirement(Building.farm, 500)),
      new Achievement("Overripe", new BuildingRequirement(Building.farm, 550)),
      new Achievement("In the green", new BuildingRequirement(Building.farm, 600)),
      //endregion
      //region Mine
      new Achievement("You know the drill", new BuildingRequirement(Building.mine, 1)),
      new Achievement("Excavation site", new BuildingRequirement(Building.mine, 50)),
      new Achievement("Hollow the planet", new BuildingRequirement(Building.mine, 100)),
      new Achievement("Can you dig it", new BuildingRequirement(Building.mine, 150)),
      new Achievement("The center of the Earth", new BuildingRequirement(Building.mine, 200)),
      new Achievement("Tectonic ambassador", new BuildingRequirement(Building.mine, 250)),
      new Achievement("Freak fracking", new BuildingRequirement(Building.mine, 300)),
      new Achievement("Romancing the stone", new BuildingRequirement(Building.mine, 350)),
      new Achievement("Mine?", new BuildingRequirement(Building.mine, 400)),
      new Achievement("Cave story", new BuildingRequirement(Building.mine, 450)),
      new Achievement("Hey now, you're a rock", new BuildingRequirement(Building.mine, 500)),
      new Achievement("Rock on", new BuildingRequirement(Building.mine, 550)),
      new Achievement(
        "Mountain out of a molehill, but like in a good way", new BuildingRequirement(
          Building
            .mine, 600
        )
      ),
      //endregion
      //region Factory
      new Achievement("Production chain", new BuildingRequirement(Building.factory, 1)),
      new Achievement("Industrial revolution", new BuildingRequirement(Building.factory, 50)),
      new Achievement("Global warming", new BuildingRequirement(Building.factory, 100)),
      new Achievement("Ultimate automation", new BuildingRequirement(Building.factory, 150)),
      new Achievement("Technocracy", new BuildingRequirement(Building.factory, 200)),
      new Achievement("Rise of the machines", new BuildingRequirement(Building.factory, 250)),
      new Achievement("Modern times", new BuildingRequirement(Building.factory, 300)),
      new Achievement("Ex machina", new BuildingRequirement(Building.factory, 350)),
      new Achievement("In full gear", new BuildingRequirement(Building.factory, 400)),
      new Achievement("In-cog-neato", new BuildingRequirement(Building.factory, 450)),
      new Achievement("Break the mold", new BuildingRequirement(Building.factory, 500)),
      new Achievement("Self-manmade man", new BuildingRequirement(Building.factory, 550)),
      new Achievement("The wheels of progress", new BuildingRequirement(Building.factory, 600)),
      //endregion
      //region Bank
      new Achievement("Pretty penny", new BuildingRequirement(Building.bank, 1)),
      new Achievement("Fit the bill", new BuildingRequirement(Building.bank, 50)),
      new Achievement("A loan in the dark", new BuildingRequirement(Building.bank, 100)),
      new Achievement("Need for greed", new BuildingRequirement(Building.bank, 150)),
      new Achievement("It's the economy, stupid", new BuildingRequirement(Building.bank, 200)),
      new Achievement("Acquire currency", new BuildingRequirement(Building.bank, 250)),
      new Achievement("The nerve of war", new BuildingRequirement(Building.bank, 300)),
      new Achievement("And I need it now", new BuildingRequirement(Building.bank, 350)),
      new Achievement("Treacle tart economics", new BuildingRequirement(Building.bank, 400)),
      new Achievement(
        "Save your breath because that's all you've got left", new BuildingRequirement(
          Building
            .bank, 450
        )
      ),
      new Achievement("Get the show on, get paid", new BuildingRequirement(Building.bank, 500)),
      new Achievement("Checks out", new BuildingRequirement(Building.bank, 550)),
      new Achievement("That's rich", new BuildingRequirement(Building.bank, 600)),
      //endregion
      //region Temple
      new Achievement("Your time to shrine", new BuildingRequirement(Building.temple, 1)),
      new Achievement("Shady sect", new BuildingRequirement(Building.temple, 50)),
      new Achievement("New-age cult", new BuildingRequirement(Building.temple, 100)),
      new Achievement("Organized religion", new BuildingRequirement(Building.temple, 150)),
      new Achievement("Fanaticism", new BuildingRequirement(Building.temple, 200)),
      new Achievement("Zealotry", new BuildingRequirement(Building.temple, 250)),
      new Achievement("Wololo", new BuildingRequirement(Building.temple, 300)),
      new Achievement("Pray on the weak", new BuildingRequirement(Building.temple, 350)),
      new Achievement("Holy cookies, grandma!", new BuildingRequirement(Building.temple, 400)),
      new Achievement("Vengeful and almighty", new BuildingRequirement(Building.temple, 450)),
      new Achievement("My world's on fire, how about yours", new BuildingRequirement(Building.temple, 500)),
      new Achievement("Living on a prayer", new BuildingRequirement(Building.temple, 550)),
      new Achievement("Preaches and cream", new BuildingRequirement(Building.temple, 600)),
      //endregion
      //region Wizard Tower
      new Achievement("Bewitched", new BuildingRequirement(Building.wizardTower, 1)),
      new Achievement("The sorcerer's apprentice", new BuildingRequirement(Building.wizardTower, 50)),
      new Achievement("Charms and enchantments", new BuildingRequirement(Building.wizardTower, 100)),
      new Achievement("Curses and maledictions", new BuildingRequirement(Building.wizardTower, 150)),
      new Achievement("Magic kingdom", new BuildingRequirement(Building.wizardTower, 200)),
      new Achievement("The wizarding world", new BuildingRequirement(Building.wizardTower, 250)),
      new Achievement(
        "And now for my next trick, I'll need a volunteer from the audience", new BuildingRequirement(
          Building
            .wizardTower, 300
        )
      ),
      new Achievement("It's a kind of magic", new BuildingRequirement(Building.wizardTower, 350)),
      new Achievement("The Prestige", new BuildingRequirement(Building.wizardTower, 400)),
      new Achievement("Spell it out for you", new BuildingRequirement(Building.wizardTower, 450)),
      new Achievement("The meteor men beg to differ", new BuildingRequirement(Building.wizardTower, 500)),
      new Achievement("Higitus figitus migitus mum", new BuildingRequirement(Building.wizardTower, 550)),
      new Achievement("Magic thinking", new BuildingRequirement(Building.wizardTower, 600)),
      //endregion
      //region Shipment
      new Achievement("Expedition", new BuildingRequirement(Building.shipment, 1)),
      new Achievement("Galactic highway", new BuildingRequirement(Building.shipment, 50)),
      new Achievement("Far far away", new BuildingRequirement(Building.shipment, 100)),
      new Achievement("Type II civilization", new BuildingRequirement(Building.shipment, 150)),
      new Achievement("We come in peace", new BuildingRequirement(Building.shipment, 200)),
      new Achievement("Parsec-masher", new BuildingRequirement(Building.shipment, 250)),
      new Achievement("It's not delivery", new BuildingRequirement(Building.shipment, 300)),
      new Achievement("Make it so", new BuildingRequirement(Building.shipment, 350)),
      new Achievement("That's just peanuts to space", new BuildingRequirement(Building.shipment, 400)),
      new Achievement("Space space space space space", new BuildingRequirement(Building.shipment, 450)),
      new Achievement("Only shooting stars", new BuildingRequirement(Building.shipment, 500)),
      new Achievement("The incredible journey", new BuildingRequirement(Building.shipment, 550)),
      new Achievement("Is there life on Mars?", new BuildingRequirement(Building.shipment, 600)),
      //endregion
      //region Alchemy
      new Achievement("Transmutation", new BuildingRequirement(Building.alchemyLab, 1)),
      new Achievement("Transmogrification", new BuildingRequirement(Building.alchemyLab, 50)),
      new Achievement("Gold member", new BuildingRequirement(Building.alchemyLab, 100)),
      new Achievement("Gild wars", new BuildingRequirement(Building.alchemyLab, 150)),
      new Achievement("The secrets of the universe", new BuildingRequirement(Building.alchemyLab, 200)),
      new Achievement("The work of a lifetime", new BuildingRequirement(Building.alchemyLab, 250)),
      new Achievement("Gold, Jerry! Gold!", new BuildingRequirement(Building.alchemyLab, 300)),
      new Achievement("All that glitters is gold", new BuildingRequirement(Building.alchemyLab, 350)),
      new Achievement("Worth its weight in lead", new BuildingRequirement(Building.alchemyLab, 400)),
      new Achievement(
        "Don't get used to yourself, you're gonna have to change", new BuildingRequirement(
          Building
            .alchemyLab, 450
        )
      ),
      new Achievement("We could all use a little change", new BuildingRequirement(Building.alchemyLab, 500)),
      new Achievement("Just a phase", new BuildingRequirement(Building.alchemyLab, 550)),
      new Achievement("Bad chemistry", new BuildingRequirement(Building.alchemyLab, 600)),
      //endregion
      //region Portals
      new Achievement("A whole new world", new BuildingRequirement(Building.portal, 1)),
      new Achievement("Now you\'re thinking", new BuildingRequirement(Building.portal, 50)),
      new Achievement("Dimensional shift", new BuildingRequirement(Building.portal, 100)),
      new Achievement("Brain-split", new BuildingRequirement(Building.portal, 150)),
      new Achievement("Realm of the Mad God", new BuildingRequirement(Building.portal, 200)),
      new Achievement("A place lost in time", new BuildingRequirement(Building.portal, 250)),
      new Achievement("Forbidden zone", new BuildingRequirement(Building.portal, 300)),
      new Achievement(
        "H̸̷͓̳̳̯̟͕̟͍͍̣͡ḛ̢̦̰̺̮̝͖͖̘̪͉͘͡ ̠̦͕̤̪̝̥̰̠̫̖̣͙̬͘ͅC̨̦̺̩̲̥͉̭͚̜̻̝̣̼͙̮̯̪o̴̡͇̘͎̞̲͇̦̲͞͡m̸̩̺̝̣̹̱͚̬̥̫̳̼̞̘̯͘ͅẹ͇̺̜́̕͢s̶̙̟̱̥̮̯̰̦͓͇͖͖̝͘͘͞", new BuildingRequirement(
          Building
            .portal, 350
        )
      ),
      new Achievement("What happens in the vortex stays in the vortex", new BuildingRequirement(Building.portal, 400)),
      new Achievement(
        "Objects in the mirror dimension are closer than they appear", new BuildingRequirement(
          Building
            .portal, 450
        )
      ),
      new Achievement("Your brain gets smart but your head gets dumb", new BuildingRequirement(Building.portal, 500)),
      new Achievement("Don't let me leave, Murph", new BuildingRequirement(Building.portal, 550)),
      new Achievement("Reduced to gibbering heaps", new BuildingRequirement(Building.portal, 600)),
      //endregion
      //region Time Machines
      new Achievement("Time warp", new BuildingRequirement(Building.timeMachine, 1)),
      new Achievement("Alternate timeline", new BuildingRequirement(Building.timeMachine, 50)),
      new Achievement("Rewriting history", new BuildingRequirement(Building.timeMachine, 100)),
      new Achievement("Time duke", new BuildingRequirement(Building.timeMachine, 150)),
      new Achievement("Forever and ever", new BuildingRequirement(Building.timeMachine, 200)),
      new Achievement("Heat death", new BuildingRequirement(Building.timeMachine, 250)),
      new Achievement(
        "cookie clicker forever and forever a hundred years cookie clicker, all day long forever, forever a hundred times, over and over cookie clicker adventures dot com", new BuildingRequirement(
          Building
            .timeMachine, 300
        )
      ), //Y u do dis? No capital letter at start and huge ass achiev name!
      new Achievement("Way back then", new BuildingRequirement(Building.timeMachine, 350)),
      new Achievement("Invited to yesterday's party", new BuildingRequirement(Building.timeMachine, 400)),
      new Achievement("Groundhog day", new BuildingRequirement(Building.timeMachine, 450)),
      new Achievement("The years start coming", new BuildingRequirement(Building.timeMachine, 500)),
      new Achievement("Caveman to cosmos", new BuildingRequirement(Building.timeMachine, 550)),
      new Achievement("Back already?", new BuildingRequirement(Building.timeMachine, 600)),
      //endregion
      //region Antimatter Condensers
      new Achievement("Antibatter", new BuildingRequirement(Building.antimatterCondenser, 1)),
      new Achievement("Quirky quarks", new BuildingRequirement(Building.antimatterCondenser, 50)),
      new Achievement("It does matter!", new BuildingRequirement(Building.antimatterCondenser, 100)),
      new Achievement("Molecular maestro", new BuildingRequirement(Building.antimatterCondenser, 150)),
      new Achievement("Walk the planck", new BuildingRequirement(Building.antimatterCondenser, 200)),
      new Achievement("Microcosm", new BuildingRequirement(Building.antimatterCondenser, 250)),
      new Achievement("Scientists baffled everywhere", new BuildingRequirement(Building.antimatterCondenser, 300)),
      new Achievement("Exotic matter", new BuildingRequirement(Building.antimatterCondenser, 350)),
      new Achievement("Downsizing", new BuildingRequirement(Building.antimatterCondenser, 400)),
      new Achievement("A matter of perspective", new BuildingRequirement(Building.antimatterCondenser, 450)),
      new Achievement("What a concept", new BuildingRequirement(Building.antimatterCondenser, 500)),
      new Achievement("Particular tastes", new BuildingRequirement(Building.antimatterCondenser, 550)),
      new Achievement("Nuclear throne", new BuildingRequirement(Building.antimatterCondenser, 600)),
      //endregion
      //region Prims
      new Achievement("Lone photon", new BuildingRequirement(Building.prism, 1)),
      new Achievement("Dazzling glimmer", new BuildingRequirement(Building.prism, 50)),
      new Achievement("Blinding flash", new BuildingRequirement(Building.prism, 100)),
      new Achievement("Unending glow", new BuildingRequirement(Building.prism, 150)),
      new Achievement("Rise and shine", new BuildingRequirement(Building.prism, 200)),
      new Achievement("Bright future", new BuildingRequirement(Building.prism, 250)),
      new Achievement("Harmony of the spheres", new BuildingRequirement(Building.prism, 300)),
      new Achievement("At the end of the tunnel", new BuildingRequirement(Building.prism, 350)),
      new Achievement("My eyes", new BuildingRequirement(Building.prism, 400)),
      new Achievement("Optical illusion", new BuildingRequirement(Building.prism, 450)),
      new Achievement("You'll never shine if you don't glow", new BuildingRequirement(Building.prism, 500)),
      new Achievement("A light snack", new BuildingRequirement(Building.prism, 550)),
      new Achievement("Making light of the situation", new BuildingRequirement(Building.prism, 600)),
      //endregion
      //region Chancemakers
      new Achievement("Lucked out", new BuildingRequirement(Building.chancemaker, 1)),
      new Achievement("What are the odds", new BuildingRequirement(Building.chancemaker, 50)),
      new Achievement("Grandma needs a new pair of shoes", new BuildingRequirement(Building.chancemaker, 100)),
      new Achievement("Million to one shot, doc", new BuildingRequirement(Building.chancemaker, 150)),
      new Achievement("As luck would have it", new BuildingRequirement(Building.chancemaker, 200)),
      new Achievement("Ever in your favor", new BuildingRequirement(Building.chancemaker, 250)),
      new Achievement("Be a lady", new BuildingRequirement(Building.chancemaker, 300)),
      new Achievement("Dicey business", new BuildingRequirement(Building.chancemaker, 350)),
      new Achievement("Maybe a chance in hell, actually", new BuildingRequirement(Building.chancemaker, 400)),
      new Achievement("Jackpot", new BuildingRequirement(Building.chancemaker, 450)),
      new Achievement("You'll never know if you don't go", new BuildingRequirement(Building.chancemaker, 500)),
      new Achievement("Tempting fate", new BuildingRequirement(Building.chancemaker, 550)),
      new Achievement(
        "Flip a cookie. Chips, I win. Crust, you lose.", new BuildingRequirement(
          Building
            .chancemaker, 600
        )
      ),
      //endregion
      //region Fractal Engines
      new Achievement("Self-contained", new BuildingRequirement(Building.fractalEngine, 1)),
      new Achievement("Threw you for a loop", new BuildingRequirement(Building.fractalEngine, 50)),
      new Achievement("The sum of its parts", new BuildingRequirement(Building.fractalEngine, 100)),
      new Achievement("Bears repeating", new BuildingRequirement(Building.fractalEngine, 150)),
      new Achievement("More of the same", new BuildingRequirement(Building.fractalEngine, 200)),
      new Achievement("Last recurse", new BuildingRequirement(Building.fractalEngine, 250)),
      new Achievement("Out of one, many", new BuildingRequirement(Building.fractalEngine, 300)),
      new Achievement("An example of recursion", new BuildingRequirement(Building.fractalEngine, 350)),
      new Achievement(
        "For more information on this achievement, please refer to its title", new BuildingRequirement(
          Building
            .fractalEngine, 400
        )
      ),
      new Achievement("I'm so meta, even this achievement", new BuildingRequirement(Building.fractalEngine, 450)),
      new Achievement("Never get bored", new BuildingRequirement(Building.fractalEngine, 500)),
      new Achievement("Tautological", new BuildingRequirement(Building.fractalEngine, 550)),
      new Achievement("In and of itself", new BuildingRequirement(Building.fractalEngine, 600)),
      //endregion
      //region All buildings
      new Achievement("One with everything", BuildingRequirement.generateRequirementsForAllBuildings(1): _*),
      new Achievement("Centennial", BuildingRequirement.generateRequirementsForAllBuildings(100): _*),
      new Achievement("Centennial and a half", BuildingRequirement.generateRequirementsForAllBuildings(150): _*),
      new Achievement("Bicentennial", BuildingRequirement.generateRequirementsForAllBuildings(200): _*),
      new Achievement("Bicentennial and a half", BuildingRequirement.generateRequirementsForAllBuildings(250): _*),
      new Achievement("Tricentennial", BuildingRequirement.generateRequirementsForAllBuildings(300): _*),
      new Achievement("Tricentennial and a half", BuildingRequirement.generateRequirementsForAllBuildings(350): _*),
      new Achievement("Quadricentennial", BuildingRequirement.generateRequirementsForAllBuildings(400): _*),
      new Achievement("Quadricentennial and a half", BuildingRequirement.generateRequirementsForAllBuildings(450): _*),
      new Achievement("Quincentennial", BuildingRequirement.generateRequirementsForAllBuildings(500): _*),
      new Achievement(
        "Base 10", Building
          .all
          .map(building => new BuildingRequirement(building, (Building.all.length - building.id.toInt) * 10))
          .toSeq: _*
      ),
      new Achievement(
        "Mathematician", Building
          .all
          .map(building => new BuildingRequirement(
            building, math.min(
              128, math
                .pow(2, Building.all.length - 1 - building.id)
                .toInt
            )
          )
          )
          .toSeq: _*
      ),
      //endregion
    ).map(upgrade => upgrade.name -> upgrade).toMap
}
