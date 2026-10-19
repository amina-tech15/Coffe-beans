package com.example.data

import com.example.R
import com.example.model.*

object CoffeeData {

    val architecturalShowcase = listOf(
        ArchitecturalView(
            id = "storefront",
            title = "Modern Storefront & Facade",
            category = "Exterior Architecture",
            drawableRes = R.drawable.img_storefront,
            headline = "Sleek Glass Facade & 3D Illuminated '8oz COFFEE' Signage",
            description = "Located on the prime street frontage of Neelam Commercial in DC Colony, Gujranwala. Designed with floor-to-ceiling double-glazed tempered glass framed in matte black powder-coated steel. The glowing warm amber 3D channel letter signage creates an iconic street presence during golden hour and late-night strolls.",
            designHighlights = listOf(
                "Tempered acoustic glass minimizing exterior street noise",
                "Bold backlit 3000K warm LED '8oz COFFEE' channel lettering",
                "Outdoor hardwood bench paired with terracotta planter urns",
                "Deep recessed entrance welcoming guests from Neelam Commercial promenade"
            ),
            architecturalSpecs = listOf(
                "Frontage Width" to "32 Feet Continuous Glazing",
                "Signage Material" to "Laser-cut Matte Black Steel & Acrylic Glow",
                "Color Scheme" to "Sophisticated Black (#141211), Cream & Coffee Brown",
                "Lighting Temp" to "3000K Cinematic Warm Amber",
                "Location" to "Plot 14, Neelam Commercial, DC Colony, Gujranwala"
            ),
            atmosphereNotes = "Inviting, photorealistic, contemporary commercial café proportioned perfectly for Gujranwala's vibrant lifestyle.",
            lightingMood = "Twilight Golden Glow"
        ),
        ArchitecturalView(
            id = "espresso_bar",
            title = "Espresso Bar & Frappe Station",
            category = "Interior Bar Architecture",
            drawableRes = R.drawable.img_espresso_bar,
            headline = "Monolithic White Carrera Counter & Multi-Group Custom Machine",
            description = "The centerpiece of 8oz Coffee: a sculpted marble bar contrasting with dark walnut fluted millwork. Houses an Italian dual-boiler commercial espresso machine, specialized precision coffee grinders, an artisanal caramel frappe blending station, and a pristine temperature-controlled bakery showcase.",
            designHighlights = listOf(
                "Continuous book-matched Carrera marble service counter",
                "Dedicated caramel frappe blending station with cold-pour rinse sink",
                "Handcrafted blown-glass & brass pendant lamps over counter",
                "Integrated pastry vitrine with ambient micro-illumination"
            ),
            architecturalSpecs = listOf(
                "Counter Height" to "920mm Ergonomic Barista Workline",
                "Worktop Material" to "Polished Italian Carrera Marble Slab",
                "Espresso Setup" to "Custom Chrome Multi-Group Pressure Profiler",
                "Backbar Finishes" to "Matte Black Steel & Fluted Walnut Timber",
                "Frappe Zone" to "Sound-Insulated Commercial Vitamix Station"
            ),
            atmosphereNotes = "A theater of coffee craftsmanship where the aroma of freshly roasted beans and steaming espresso meets acoustic warmth.",
            lightingMood = "Focused Task & Ambient Brass Warmth"
        ),
        ArchitecturalView(
            id = "cozy_seating",
            title = "Cozy Lounge & Study Hub",
            category = "Interior Seating Experience",
            drawableRes = R.drawable.img_cozy_interior,
            headline = "Plush Banquettes, Fluted Wood Panels & Student Workstations",
            description = "Carefully zoned to offer quiet focus for university students (GIFT, PU, FAST) and cozy booths for couples and coffee connoisseurs. Custom cognac leather banquettes, solid oak bistro tables, hidden international power sockets, and lush indoor greenery create an Instagram-worthy urban retreat.",
            designHighlights = listOf(
                "Cognac leather banquette seating with high acoustic back cushions",
                "Every seat equipped with dual universal AC power sockets and USB-C Fast Charge",
                "Acoustic fluted oak feature wall with backlit warm slogan art",
                "Lush indoor Ficus Lyrata and trailing pothos purifying ambient air"
            ),
            architecturalSpecs = listOf(
                "Seating Capacity" to "54 Indoor Guests + 12 Al Fresco",
                "Furniture Fabric" to "Full-grain Cognac Leather & Natural Oak",
                "Acoustics" to "Perforated Acoustic Felt Ceiling & Wood Baffles",
                "Connectivity" to "Dedicated 500Mbps Fiber WiFi 6 Mesh",
                "Wall Feature" to "Vertical Oak Battens with Ambient Halo Glow"
            ),
            atmosphereNotes = "Vibrant yet relaxed — the beloved study haunt and social anchor of DC Colony Gujranwala.",
            lightingMood = "Soft Indirect Warmth (2700K)"
        ),
        ArchitecturalView(
            id = "signature_drinks",
            title = "Signature Brews & Branded Cups",
            category = "Brand Identity & Craft",
            drawableRes = R.drawable.img_signature_drinks,
            headline = "The Golden 8oz Ratio, Iced Caramel Frappe & Branded Takeaway",
            description = "At 8oz Coffee, the 8-ounce cup represents perfection: 2 shots of rich espresso balanced with 6 ounces of velvety microfoam milk. Paired with our bestselling iced caramel frappe crowned with whipped cream and golden butterscotch drizzle, presented in custom embossed matte black & eco-kraft takeaway cups.",
            designHighlights = listOf(
                "Handcrafted thick-walled 8oz ceramic demitasse cups for heat retention",
                "Signature Iced Caramel Frappe with house-made salted butter caramel",
                "Minimalist matte black takeaway cups with crisp white '8oz' typography",
                "Eco-conscious double-wall biodegradable kraft paper cups"
            ),
            architecturalSpecs = listOf(
                "Espresso Dose" to "19g In / 38g Out (1:2 Precision Extraction)",
                "Milk Texture" to "Silky Wet Microfoam (65°C Sweet Spot)",
                "Packaging" to "FSC-Certified Kraft with Matte Embossed Branding",
                "Bean Roast" to "Medium-Dark Roast (Brazil Cerrado & Colombia Supremo)",
                "Signature Size" to "8oz Standard (Connoisseur Volume)"
            ),
            atmosphereNotes = "Sensory indulgence where specialty roasting meets aesthetic lifestyle packaging.",
            lightingMood = "Studio Product Glow"
        )
    )

    val menuItems = listOf(
        // Signature 8oz
        MenuItem(
            id = "sig_8oz_spanish_latte",
            name = "8oz Spanish Latte",
            category = MenuCategory.SIGNATURE_8OZ,
            basePricePkr = 780,
            description = "Double ristretto over steamed velvety milk kissed with sweet condensed milk caramel. Rich, creamy, and undeniably addictive.",
            tag = "Bestseller #1",
            imageRes = R.drawable.img_signature_drinks,
            is8ozSignature = true,
            calories = 240,
            rating = 4.98f,
            originNotes = "House DC Blend + Condensed Milk"
        ),
        MenuItem(
            id = "sig_8oz_cortado",
            name = "8oz Artisanal Cortado",
            category = MenuCategory.SIGNATURE_8OZ,
            basePricePkr = 690,
            description = "The purist's darling: Equal parts bold double espresso and lightly textured steamed milk in a thick glass tumbler.",
            tag = "Barista Pick",
            imageRes = R.drawable.img_signature_drinks,
            is8ozSignature = true,
            calories = 110,
            rating = 4.95f,
            originNotes = "Ethiopian Yirgacheffe Single Origin"
        ),
        MenuItem(
            id = "sig_8oz_flat_white",
            name = "8oz Aussie Flat White",
            category = MenuCategory.SIGNATURE_8OZ,
            basePricePkr = 720,
            description = "A thin veil of silky microfoam folded seamlessly into double espresso for maximum coffee depth and velvety mouthfeel.",
            tag = "Silky Microfoam",
            imageRes = R.drawable.img_signature_drinks,
            is8ozSignature = true,
            calories = 145,
            rating = 4.92f,
            originNotes = "100% Specialty Arabica"
        ),
        MenuItem(
            id = "sig_8oz_pistachio_latte",
            name = "8oz Pistachio Velvet Latte",
            category = MenuCategory.SIGNATURE_8OZ,
            basePricePkr = 890,
            description = "Roasted Sicilian pistachio paste blended with double shot espresso and steamed milk, garnished with crushed pistachio crumble.",
            tag = "Chef Special",
            imageRes = R.drawable.img_signature_drinks,
            is8ozSignature = true,
            calories = 280,
            rating = 4.97f,
            originNotes = "Real Pistachio Puree & Arabica"
        ),

        // Frappes & Chilled
        MenuItem(
            id = "frappe_signature_caramel",
            name = "8oz Signature Caramel Frappe",
            category = MenuCategory.FRAPPES_CHILLED,
            basePricePkr = 860,
            description = "Blended fresh espresso, rich milk, crushed ice, and salted caramel sauce, topped with high peak whipped cream and a decadent caramel drizzle.",
            tag = "Campus Favorite",
            imageRes = R.drawable.img_signature_drinks,
            is8ozSignature = false,
            calories = 360,
            rating = 4.99f,
            originNotes = "House-Made Salted Butter Caramel"
        ),
        MenuItem(
            id = "frappe_lotus_biscoff",
            name = "Lotus Biscoff Cream Frappe",
            category = MenuCategory.FRAPPES_CHILLED,
            basePricePkr = 890,
            description = "Belgian Lotus Biscoff spread blended with espresso, vanilla cream, and topped with crunchy Biscoff biscuit crumb.",
            tag = "Viral Hit",
            imageRes = R.drawable.img_signature_drinks,
            is8ozSignature = false,
            calories = 410,
            rating = 4.96f,
            originNotes = "Original Lotus Speculoos"
        ),
        MenuItem(
            id = "iced_spanish_latte",
            name = "Iced Spanish Latte (Over Rock Ice)",
            category = MenuCategory.FRAPPES_CHILLED,
            basePricePkr = 820,
            description = "Layered condensed milk, chilled whole milk, and slow-poured espresso over clear artisanal ice cubes. Beautiful gradient layers.",
            tag = "Refreshing",
            imageRes = R.drawable.img_signature_drinks,
            is8ozSignature = false,
            calories = 230,
            rating = 4.94f,
            originNotes = "Hand-cut Crystal Ice & Double Shot"
        ),
        MenuItem(
            id = "cold_brew_vanilla_foam",
            name = "Nitro Cold Brew with Vanilla Cold Foam",
            category = MenuCategory.FRAPPES_CHILLED,
            basePricePkr = 790,
            description = "18-hour slow steeped cold brew poured crisp and smooth, crowned with thick, sweet vanilla bean cold froth.",
            tag = "Low Acidity",
            imageRes = R.drawable.img_signature_drinks,
            is8ozSignature = false,
            calories = 120,
            rating = 4.91f,
            originNotes = "18-Hour Colombian Steep"
        ),

        // Hot Espresso & Brews
        MenuItem(
            id = "hot_cappuccino",
            name = "Traditional 8oz Cappuccino",
            category = MenuCategory.HOT_ESPRESSO,
            basePricePkr = 650,
            description = "Harmonious one-third espresso, one-third steamed milk, and one-third dense microfoam dusting with Dutch cocoa powder.",
            tag = "Classic",
            imageRes = R.drawable.img_espresso_bar,
            is8ozSignature = true,
            calories = 130,
            rating = 4.89f,
            originNotes = "Valrhona Cocoa & Double Shot"
        ),
        MenuItem(
            id = "hot_caffe_latte",
            name = "Caffè Latte with Latte Art",
            category = MenuCategory.HOT_ESPRESSO,
            basePricePkr = 680,
            description = "Silky steamed milk poured delicately over rich double espresso featuring rosetta or tulip latte art poured by our head barista.",
            tag = "Comfort Brew",
            imageRes = R.drawable.img_espresso_bar,
            is8ozSignature = false,
            calories = 160,
            rating = 4.88f,
            originNotes = "Fresh Farm Milk Daily"
        ),
        MenuItem(
            id = "hot_americano",
            name = "Long Black / Americano",
            category = MenuCategory.HOT_ESPRESSO,
            basePricePkr = 580,
            description = "Double espresso extracted directly over 93°C hot filtered water, preserving a thick golden crema on top.",
            tag = "Zero Sugar",
            imageRes = R.drawable.img_espresso_bar,
            is8ozSignature = false,
            calories = 5,
            rating = 4.87f,
            originNotes = "Reverse Osmosis Triple-Filtered Water"
        ),

        // Artisan Bakery & Pastries
        MenuItem(
            id = "bakery_almond_croissant",
            name = "Artisan Almond Croissant",
            category = MenuCategory.ARTISAN_BAKERY,
            basePricePkr = 620,
            description = "Twice-baked French butter croissant filled with almond frangipane cream and topped with toasted almond flakes & powdered sugar.",
            tag = "Freshly Baked",
            imageRes = R.drawable.img_espresso_bar,
            is8ozSignature = false,
            calories = 380,
            rating = 4.95f,
            originNotes = "French Pure Butter Laminate"
        ),
        MenuItem(
            id = "bakery_lotus_cheesecake",
            name = "New York Lotus Biscoff Cheesecake",
            category = MenuCategory.ARTISAN_BAKERY,
            basePricePkr = 780,
            description = "Rich, dense cream cheese on a crunchy Biscoff base, generously glazed with warm melted speculoos cookie spread.",
            tag = "Decadent",
            imageRes = R.drawable.img_espresso_bar,
            is8ozSignature = false,
            calories = 460,
            rating = 4.97f,
            originNotes = "Philadelphia Cream Cheese"
        ),
        MenuItem(
            id = "bakery_fudge_brownie",
            name = "Warm Belgian Dark Chocolate Brownie",
            category = MenuCategory.ARTISAN_BAKERY,
            basePricePkr = 590,
            description = "Gooey center with 70% dark Belgian cocoa and roasted walnuts, served warm with a side dollop of vanilla bean cream.",
            tag = "Warm & Gooey",
            imageRes = R.drawable.img_espresso_bar,
            is8ozSignature = false,
            calories = 420,
            rating = 4.93f,
            originNotes = "Callebaut 70% Dark Chocolate"
        ),

        // Gourmet Savory
        MenuItem(
            id = "savory_roast_beef_sourdough",
            name = "Toasted Roast Beef & Aged Cheddar Panini",
            category = MenuCategory.SAVORY_BITES,
            basePricePkr = 950,
            description = "Tender sliced roast beef, caramelized onions, melted aged cheddar, and Dijon honey mustard on toasted rustic sourdough.",
            tag = "Gourmet Lunch",
            imageRes = R.drawable.img_cozy_interior,
            is8ozSignature = false,
            calories = 490,
            rating = 4.91f,
            originNotes = "Artisanal Fermented Sourdough"
        ),
        MenuItem(
            id = "savory_croissant_egg_cheese",
            name = "Scrambled Herb Egg & Smoked Cheese Croissant",
            category = MenuCategory.SAVORY_BITES,
            basePricePkr = 750,
            description = "Fluffy pasture-raised scrambled eggs with chives, smoked gouda, and fresh baby spinach tucked into a buttery warm croissant.",
            tag = "All-Day Breakfast",
            imageRes = R.drawable.img_cozy_interior,
            is8ozSignature = false,
            calories = 410,
            rating = 4.89f,
            originNotes = "Organic Farm Eggs & Gouda"
        )
    )

    val customerReviews = listOf(
        CustomerReview(
            author = "Hamza R.",
            role = "GIFT University Student",
            comment = "8oz Coffee has completely elevated Gujranwala’s café scene. The Spanish Latte in the signature 8oz cup is unmatched, and every seat in the DC Colony branch actually has working high-speed charging outlets!",
            rating = 5,
            favoriteDrink = "8oz Spanish Latte"
        ),
        CustomerReview(
            author = "Dr. Zainab Tariq",
            role = "Resident, DC Colony",
            comment = "The exterior storefront is so sleek with that floor-to-ceiling glass and glowing sign. Walking into Neelam Commercial at 9 PM and smelling the freshly ground beans feels like a high-end European café right here.",
            rating = 5,
            favoriteDrink = "Signature Caramel Frappe"
        ),
        CustomerReview(
            author = "Bilal & Maryam",
            role = "Couple & Coffee Enthusiasts",
            comment = "Our favorite cozy date spot! The plush leather booths and warm lighting make conversations so relaxed. Don't skip the Almond Croissant and Cortado.",
            rating = 5,
            favoriteDrink = "8oz Artisanal Cortado"
        )
    )

    val branchDetails = mapOf(
        "Location" to "Plot 14, Neelam Commercial Market, DC Colony, Gujranwala, Punjab, Pakistan",
        "Hours" to "8:00 AM – 1:00 AM Daily (Midnight Hangouts)",
        "WiFi Network" to "8ozCoffee_HighSpeed_Guest",
        "WiFi Password" to "GoldenRatio8oz",
        "Contact Phone" to "+92 55 389 8800",
        "Student Perk" to "15% Discount with any valid University Student ID",
        "Parking" to "Spacious dedicated front parking in Neelam Commercial square"
    )
}
