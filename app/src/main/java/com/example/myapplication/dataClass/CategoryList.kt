package com.example.myapplication.dataClass

enum class SpinWebCategory(val value: Int) {
    Drugs(1010), Pornography(1011), Gambling(1027), FileSharing(1031), VPNProxy(1058), Nudity(1052), AlcoholAndTobacco(
        1076
    ),
    ProneToPorn(10002), Youtube(10005), CultAndOccult(1008), WebAds(1052), SocialNetworking(1014), Plagiarism(
        1053
    ),
    Dating(1018), Gross(1054), SexEducation(1019), MalwareSites(1056), Religion(1020), PhishingAndFrauds(
        1057
    ),
    EntertainmentAndArts(1021), SpywareAndAdware(1059), PersonalSitesAndBlogs(1022), Illegal(1064), StreamingMedia(
        1025
    ),
    InternetCommunications(1066), JobSearch(1026), Botnets(1067), SharewareAndFreeware(1030), Abortion(
        1068
    ),
    Hacking(1033), HealthAndMedicine(1069), Games(1034), SpamURLs(1072), Weapons(1036), ParkedDomains(
        1075
    ),
    PayToSurf(1037), ImageAndVideoSearch(1078), HuntingAndFishing(1038), FashionAndBeauty(1079), Questionable(
        1044
    ),
    WebHostingSites(2002), HateAndRacism(1046), PersonalStorage(1047), ViolenceAndSuicide(1048), KeyloggersAndMonitoring(
        1049
    ),
    SearchEngines(1050), ProneToBadContent(1000);

    companion object {
        fun fromValue(value: Int): SpinWebCategory? = entries.find { it.value == value }
    }
}

enum class BlocksiCategory(val value: Int) {
    Malicious(100), Phishing(101), SpamUrl(102),

    DrugAbuse(200), Hacking(201), IllegalOrUnethical(202), Discrimination(203), ExplicitViolence(204), ExtremistGroups(
        205
    ),
    ProxyAvoidance(206), Plagiarism(207), ChildAbuse(208),

    AlternativeBeliefs(300), Abortion(301), OtherAdultMaterials(302), AdvocacyOrganizations(303), Gambling(
        304
    ),
    NudityAndRisque(305), Pornography(306), Dating(307), WeaponsSales(308), Marijuana(309), SexEducation(
        310
    ),
    Alcohol(311), Tobacco(312), LingerieAndSwimsuit(313), SportsHuntingAndWarGames(314),

    FreewareAndSoftwareDownloads(400), FileSharingAndStorage(401), StreamingMediaAndDownload(402), PeerToPeerFileSharing(
        403
    ),
    InternetRadioAndTV(404), InternetTelephony(405),

    FinanceAndBanking(500), SearchEnginesAndPortals(501), GeneralOrganizations(502), Business(503), InformationAndComputerSecurity(
        504
    ),
    GovernmentAndLegalOrganizations(505), InformationTechnology(506), ArmedForces(507), WebHosting(
        508
    ),
    SecureWebsites(509), WebBasedApplications(510),

    Advertising(600), BrokerageAndTrading(601), Games(602), WebBasedEmail(603), Entertainment(604), ArtsAndCulture(
        605
    ),
    Education(606), HealthAndWellness(607), JobSearch(608), Medicine(609), NewsAndMedia(610), SocialNetworking(
        611
    ),
    PoliticalOrganizations(612), Reference(613), GlobalReligion(614), ShoppingAndAuction(615), SocietyAndLifestyles(
        616
    ),
    Sports(617), Travel(618), PersonalVehicles(619), DynamicContent(620), MeaninglessContent(621), Folklore(
        622
    ),
    WebChat(623), InstantMessaging(624), NewsgroupsAndMessageBoards(625), DigitalPostcards(626), ChildEducation(
        627
    ),
    RealEstate(628), RestaurantAndDining(629), PersonalWebsitesAndBlogs(630), ContentServers(631), DomainParking(
        632
    ),
    PersonalPrivacy(633);

    companion object {
        fun fromValue(value: Int): BlocksiCategory? = entries.find { it.value == value }
    }
}

enum class GeneralCategory {
    SecurityRisk, IllegalUnethical, AdultMature, BandwidthConsuming, Business, Personal
}

data class WebCategory(
    val name: String,
    val spin: List<SpinWebCategory>,
    val description: String,
    val blocksi: List<BlocksiCategory>,
    val generalCategory: GeneralCategory
)

val categoryList = listOf(
    WebCategory(
        name = "Malicious Websites",
        description = "Sites that host software that is covertly downloaded to a user's machine to collect information and monitor user activity, and sites that are infected with destructive or malicious software, specifically designed to damage, disrupt, attack or manipulate computer systems without the user's consent, such as virus or trojan horse.",
        spin = listOf(
            SpinWebCategory.MalwareSites,
            SpinWebCategory.PhishingAndFrauds,
            SpinWebCategory.SpywareAndAdware
        ),
        blocksi = listOf(
            BlocksiCategory.Malicious
        ),
        generalCategory = GeneralCategory.SecurityRisk
    ), WebCategory(
        name = "Phishing",
        description = "Counterfeit web pages that duplicate legitimate business web pages for the purpose of eliciting financial, personal or other private information from the users.",
        spin = listOf(SpinWebCategory.MalwareSites, SpinWebCategory.PhishingAndFrauds),
        blocksi = listOf(BlocksiCategory.Phishing),
        generalCategory = GeneralCategory.SecurityRisk
    ), WebCategory(
        name = "Spam URLs",
        description = "Websites or webpages whose URLs are found in spam emails. These webpages often advertise sex sites, fraudulent wares, and other potentially offensive materials.",
        spin = listOf(SpinWebCategory.SpywareAndAdware, SpinWebCategory.SpamURLs),
        blocksi = listOf(BlocksiCategory.SpamUrl),
        generalCategory = GeneralCategory.SecurityRisk
    ), WebCategory(
        name = "Drug Abuse",
        description = "Websites that feature information on illegal drug activities including: drug promotion, preparation, cultivation, trafficking, distribution, solicitation, etc.",
        spin = listOf(SpinWebCategory.Drugs),
        blocksi = listOf(BlocksiCategory.DrugAbuse, BlocksiCategory.Marijuana),
        generalCategory = GeneralCategory.IllegalUnethical
    ), WebCategory(
        name = "Hacking",
        description = "Websites that depict illicit activities surrounding the unauthorized modification or access to programs, computers, equipment and websites.",
        spin = listOf(SpinWebCategory.Hacking),
        blocksi = listOf(BlocksiCategory.Hacking),
        generalCategory = GeneralCategory.IllegalUnethical
    ), WebCategory(
        name = "Illegal or Unethical",
        description = "Websites that feature information, methods, or instructions on fraudulent actions or unlawful conduct (non-violent) such as scams, counterfeiting, tax evasion, petty theft, blackmail, etc.",
        spin = listOf(SpinWebCategory.Illegal),
        blocksi = listOf(BlocksiCategory.IllegalOrUnethical),
        generalCategory = GeneralCategory.IllegalUnethical
    ), WebCategory(
        name = "Bad Content",
        description = "Websites that are normally good but may contain illegal or unethical contents",
        spin = listOf(SpinWebCategory.ProneToBadContent),
        blocksi = listOf(),
        generalCategory = GeneralCategory.IllegalUnethical
    ), WebCategory(
        name = "Discrimination ,Hate or Racism",
        description = "Sites that promote the identification of racial groups, the denigration or subjection of groups, or the superiority of any group.",
        spin = listOf(SpinWebCategory.HateAndRacism),
        blocksi = listOf(BlocksiCategory.Discrimination),
        generalCategory = GeneralCategory.IllegalUnethical
    ), WebCategory(
        name = "Explicit Violence",
        description = "This category includes sites that depict offensive material on brutality, death, cruelty, acts of abuse, mutilation, etc.",
        spin = listOf(SpinWebCategory.ViolenceAndSuicide),
        blocksi = listOf(BlocksiCategory.ExplicitViolence),
        generalCategory = GeneralCategory.IllegalUnethical
    ), WebCategory(
        name = "Gross Content",
        description = "Sites that includes dirty content",
        spin = listOf(SpinWebCategory.Gross),
        blocksi = listOf(BlocksiCategory.ExplicitViolence),
        generalCategory = GeneralCategory.IllegalUnethical
    ), WebCategory(
        name = "Extremist Groups (Cults)",
        description = "Sites that feature radical militia groups or movements with aggressive anti-government convictions or beliefs.",
        spin = listOf(SpinWebCategory.CultAndOccult),
        blocksi = listOf(BlocksiCategory.ExtremistGroups),
        generalCategory = GeneralCategory.IllegalUnethical
    ), WebCategory(
        name = "Proxy Avoidance",
        description = "Websites that provide information or tools on how to bypass Internet access controls and browse the Web anonymously, includes anonymous proxy servers.",
        spin = listOf(SpinWebCategory.VPNProxy),
        blocksi = listOf(BlocksiCategory.ProxyAvoidance),
        generalCategory = GeneralCategory.IllegalUnethical
    ), WebCategory(
        name = "Plagiarism",
        description = "Websites that provide, distribute or sell school essays, projects, or diplomas.",
        spin = listOf(SpinWebCategory.Plagiarism),
        blocksi = listOf(BlocksiCategory.Plagiarism),
        generalCategory = GeneralCategory.IllegalUnethical
    ), WebCategory(
        name = "Child Abuse",
        description = "Websites that have been verified by the Internet Watch Foundation to contain or distribute images of non-adult children that are depicted in a state of abuse.",
        spin = listOf(SpinWebCategory.ViolenceAndSuicide, SpinWebCategory.Questionable),
        blocksi = listOf(BlocksiCategory.ChildAbuse),
        generalCategory = GeneralCategory.IllegalUnethical
    ), WebCategory(
        name = "Alternative Beliefs",
        description = "Websites that provide information about or promote religions not specified in Traditional Religions or other unconventional, cultic, or folkloric beliefs and practices. Sites that promote or offer methods, means of instruction, or other resources to affect or influence real events through the use of spells, curses, magic powers, satanic or supernatural beings.",
        spin = listOf(SpinWebCategory.CultAndOccult),
        blocksi = listOf(BlocksiCategory.AlternativeBeliefs),
        generalCategory = GeneralCategory.AdultMature
    ), WebCategory(
        name = "Abortion",
        description = "Websites pertaining to abortion data, information, legal issues, and organizations.",
        spin = listOf(SpinWebCategory.Abortion),
        blocksi = listOf(BlocksiCategory.Abortion),
        generalCategory = GeneralCategory.AdultMature
    ), WebCategory(
        name = "Adult Materials",
        description = "Mature content websites (18+ years and over) that feature or promote sexuality, strip clubs, sex shops, etc. excluding sex education, without the intent to sexually arouse.",
        spin = listOf(SpinWebCategory.ProneToPorn, SpinWebCategory.Nudity),
        blocksi = listOf(BlocksiCategory.OtherAdultMaterials, BlocksiCategory.NudityAndRisque),
        generalCategory = GeneralCategory.AdultMature
    ), WebCategory(
        name = "Advocacy Organizations",
        description = "This category caters to organizations that campaign or lobby for a cause by building public awareness, raising support, influencing public policy, etc.",
        spin = listOf(),
        blocksi = listOf(BlocksiCategory.AdvocacyOrganizations),
        generalCategory = GeneralCategory.AdultMature
    ), WebCategory(
        name = "Gambling",
        description = "Sites that cater to gambling activities such as betting, lotteries, casinos, including gaming information, instruction, and statistics.",
        spin = listOf(SpinWebCategory.Gambling),
        blocksi = listOf(BlocksiCategory.Gambling),
        generalCategory = GeneralCategory.AdultMature
    ), WebCategory(
        name = "Nudity and Risque",
        description = "Mature content websites (18+ years and over) that depict the human body in full or partial nudity without the intent to sexually arouse.",
        spin = listOf(SpinWebCategory.Nudity),
        blocksi = listOf(BlocksiCategory.NudityAndRisque),
        generalCategory = GeneralCategory.AdultMature
    ), WebCategory(
        name = "Pornography",
        description = "Mature content websites (18+ years and over) which present or display sexual acts with the intent to sexually arouse and excite.",
        spin = listOf(SpinWebCategory.Pornography, SpinWebCategory.ProneToPorn),
        blocksi = listOf(BlocksiCategory.Pornography),
        generalCategory = GeneralCategory.AdultMature
    ), WebCategory(
        name = "Dating",
        description = "Websites that allow individuals to make contact and communicate with each other over the Internet, usually with the objective of developing a personal, romantic, or sexual relationship.",
        spin = listOf(SpinWebCategory.Dating),
        blocksi = listOf(BlocksiCategory.Dating),
        generalCategory = GeneralCategory.AdultMature
    ), WebCategory(
        name = "Weapons (Sales)",
        description = "Websites that feature the legal promotion or sale of weapons such as hand guns, knives, rifles, explosives, etc.",
        spin = listOf(SpinWebCategory.Weapons),
        blocksi = listOf(BlocksiCategory.WeaponsSales),
        generalCategory = GeneralCategory.AdultMature
    ), WebCategory(
        name = "Sex Education",
        description = "Educational websites that provide information or discuss sex and sexuality, without utilizing pornographic materials.",
        spin = listOf(SpinWebCategory.SexEducation),
        blocksi = listOf(BlocksiCategory.SexEducation),
        generalCategory = GeneralCategory.AdultMature

    ), WebCategory(
        name = "Alcohol, Cigarette,Vaping and Tobacco",
        description = "Websites which legally promote or sell alcohol products,tobacco,vapes,cigars,cigarette and accessories.",
        spin = listOf(SpinWebCategory.AlcoholAndTobacco),
        blocksi = listOf(
            BlocksiCategory.Tobacco, BlocksiCategory.Tobacco, BlocksiCategory.Marijuana
        ),
        generalCategory = GeneralCategory.AdultMature
    ), WebCategory(
        name = "Lingerie and Swimsuit",
        description = "Websites that utilize images of semi-nude models in lingerie, undergarments, and swimwear for the purpose of selling or promoting such items.",
        spin = listOf(),
        blocksi = listOf(BlocksiCategory.LingerieAndSwimsuit),
        generalCategory = GeneralCategory.AdultMature
    ), WebCategory(
        name = "Sports Hunting and War Games",
        description = "Web pages that feature sport hunting, war games, paintball facilities, etc. Includes all related clubs, organizations and groups.",
        spin = listOf(SpinWebCategory.HuntingAndFishing),
        blocksi = listOf(BlocksiCategory.SportsHuntingAndWarGames),
        generalCategory = GeneralCategory.AdultMature
    ), WebCategory(
        name = "Questionable",
        description = "Websites with potentially dubious or suspicious content.",
        spin = listOf(SpinWebCategory.Questionable),
        blocksi = emptyList(),
        generalCategory = GeneralCategory.AdultMature
    ), WebCategory(
        name = "Freeware and Software Downloads",
        description = "Sites whose primary function is to provide freeware and software downloads. Cell phone ringtones/images/games, computer software updates for free downloads are all included in this category.",
        spin = listOf(SpinWebCategory.SharewareAndFreeware),
        blocksi = listOf(BlocksiCategory.FreewareAndSoftwareDownloads),
        generalCategory = GeneralCategory.BandwidthConsuming
    ), WebCategory(
        name = "File Sharing and Storage",
        description = "Websites that permit users to utilize Internet servers to store personal files or for sharing, such as with photos.",
        spin = listOf(SpinWebCategory.FileSharing, SpinWebCategory.PersonalStorage),
        blocksi = listOf(BlocksiCategory.FileSharingAndStorage),
        generalCategory = GeneralCategory.BandwidthConsuming
    ), WebCategory(
        name = "Streaming Media and Download",
        description = "Websites that allow the downloading of MP3 or other multimedia files.",
        spin = listOf(SpinWebCategory.StreamingMedia),
        blocksi = listOf(BlocksiCategory.StreamingMediaAndDownload),
        generalCategory = GeneralCategory.BandwidthConsuming

    ), WebCategory(
        name = "Pay To Surf",
        description = "Websites incentivizing users to browse for monetary rewards.",
        spin = listOf(SpinWebCategory.PayToSurf),
        blocksi = emptyList(),
        generalCategory = GeneralCategory.BandwidthConsuming
    ), WebCategory(
        name = "Image And Video Search",
        description = "Websites that specialize in searching for images and videos.",
        spin = listOf(SpinWebCategory.ImageAndVideoSearch),
        blocksi = emptyList(),
        generalCategory = GeneralCategory.BandwidthConsuming
    ), WebCategory(
        name = "Key Logger and Monitoring",
        description = "",
        spin = listOf(SpinWebCategory.KeyloggersAndMonitoring),
        blocksi = emptyList(),
        generalCategory = GeneralCategory.BandwidthConsuming
    ), WebCategory(
        name = "Peer-to-peer File Sharing",
        description = "Websites that allow users to share files and data storage between each other.",
        spin = listOf(),
        blocksi = listOf(BlocksiCategory.PeerToPeerFileSharing),
        generalCategory = GeneralCategory.BandwidthConsuming

    ), WebCategory(
        name = "Internet Radio and TV",
        description = "Websites that broadcast radio or TV communications over the Internet.",
        spin = listOf(),
        blocksi = listOf(BlocksiCategory.InternetRadioAndTV),
        generalCategory = GeneralCategory.BandwidthConsuming

    ), WebCategory(
        name = "Internet Telephony",
        description = "Websites that enable telephone communications over the Internet.",
        spin = listOf(),
        blocksi = listOf(BlocksiCategory.InternetTelephony),
        generalCategory = GeneralCategory.BandwidthConsuming

    ), WebCategory(
        name = "Finance and Banking",
        description = "Financial Data and Services -- Sites that offer news and quotations on stocks, bonds, and other investment vehicles, investment advice, but not online trading. Includes banks, credit unions, credit cards, and insurance. Mortgage/insurance brokers apply here as opposed to Brokerage and Trading.",
        spin = listOf(),
        blocksi = listOf(BlocksiCategory.FinanceAndBanking),
        generalCategory = GeneralCategory.Business
    ), WebCategory(
        name = "Search Engines and Portals",
        description = "Sites that support searching the Web, news groups, or indices/directories. Sites of search engines that provide info exclusively for shopping or comparing prices, however, fall in Shopping and Auction.",
        spin = listOf(SpinWebCategory.SearchEngines),
        blocksi = listOf(BlocksiCategory.SearchEnginesAndPortals),
        generalCategory = GeneralCategory.Business

    ), WebCategory(
        name = "General Organizations",
        description = "Sites that cater to groups, clubs or organisations of individuals with similar interests, either professional, social, humanitarian or recreational in nature. Social and Affiliation Organizations: Sites sponsored by or that support or offer information about organizations devoted chiefly to socializing or common interests other than philanthropy or professional advancement. Not to be confused with Advocacy Groups and Political Groups.",
        spin = listOf(),
        blocksi = listOf(BlocksiCategory.GeneralOrganizations),
        generalCategory = GeneralCategory.Business

    ), WebCategory(
        name = "Business",
        description = "Sites sponsored by or devoted to business firms, business associations, industry groups, or business in general. Information Technology companies are excluded in this category and fall in Information Technology.",
        spin = listOf(),
        blocksi = listOf(BlocksiCategory.Business),
        generalCategory = GeneralCategory.Business
    ), WebCategory(
        name = "Information and Computer Security",
        description = "Sites that provide information about or free downloadable tools for computer security, but not ordinary Freeware and Software downloading.",
        spin = listOf(),
        blocksi = listOf(BlocksiCategory.InformationAndComputerSecurity),
        generalCategory = GeneralCategory.Business
    ), WebCategory(
        name = "Government and Legal Organizations",
        description = "Government: Sites sponsored by branches, bureaus, or agencies of any level of government, except for the armed forces, including courts, police institutions, city-level government institutions. Legal Organizations: Sites that discuss or explain laws of various government entities.",
        spin = listOf(),
        blocksi = listOf(BlocksiCategory.GovernmentAndLegalOrganizations),
        generalCategory = GeneralCategory.Business
    ), WebCategory(
        name = "Information Technology",
        description = "Information Technology peripherals and services, cell phone services, cable TV/Internet suppliers.",
        spin = listOf(),
        blocksi = listOf(BlocksiCategory.InformationTechnology),
        generalCategory = GeneralCategory.Business
    ), WebCategory(
        name = "Armed Forces",
        description = "Websites related to organized military and armed forces, excluding civil and extreme military organizations.",
        spin = listOf(),
        blocksi = listOf(BlocksiCategory.ArmedForces),
        generalCategory = GeneralCategory.Business
    ), WebCategory(
        name = "Web Hosting",
        description = "Sites of organizations that provide hosting services, or top-level domain pages of Web communities.",
        spin = listOf(SpinWebCategory.WebHostingSites),
        blocksi = listOf(BlocksiCategory.WebHosting),
        generalCategory = GeneralCategory.Business
    ), WebCategory(
        name = "Secure Websites",
        description = "Sites that institute security measures such as authentication, passwords, registration, etc.",
        spin = listOf(),
        blocksi = listOf(BlocksiCategory.SecureWebsites),
        generalCategory = GeneralCategory.Business
    ), WebCategory(
        name = "Web-based Applications",
        description = "Sites that mimic desktop applications such as word processing, spreadsheets, and slide-show presentations.",
        spin = listOf(),
        blocksi = listOf(BlocksiCategory.WebBasedApplications),
        generalCategory = GeneralCategory.Business
    ), WebCategory(
        name = "Advertising",
        description = "Sites that provide advertising graphics or other ad content files, including ad servers (domain name often with ad., such as ad.yahoo.com). If a site is mainly for online transactions, it is rated as Shopping and Auctions. Includes pay-to-surf and affiliated advertising programs.",
        spin = listOf(SpinWebCategory.WebAds),
        blocksi = listOf(BlocksiCategory.Advertising),
        generalCategory = GeneralCategory.Personal
    ), WebCategory(
        name = "Botnets",
        description = "Websites associated with botnet activity, including command-and-control servers and malware distribution.",
        spin = listOf(SpinWebCategory.Botnets),
        blocksi = listOf(),
        generalCategory = GeneralCategory.Personal
    ), WebCategory(
        name = "Brokerage and Trading",
        description = "Sites that support active trading of securities and management of investments. Real estate broker does not apply here, and falls within Shopping and Auction. Sites that provide supplier and buyer info/ads do not apply here either since they do not provide trading activities.",
        spin = listOf(),
        blocksi = listOf(BlocksiCategory.BrokerageAndTrading),
        generalCategory = GeneralCategory.Personal
    ), WebCategory(
        name = "Games",
        description = "Sites that provide information about or promote electronic games, video games, computer games, role-playing games, or online games. Includes sweepstakes and giveaways. Sport games are not included in this category, but time consuming mathematic game sites that serve little education purpose are included in this category.",
        spin = listOf(SpinWebCategory.Games),
        blocksi = listOf(BlocksiCategory.Games),
        generalCategory = GeneralCategory.Personal
    ), WebCategory(
        name = "Web-based Email",
        description = "Sites that allow users to utilize electronic mail services.",
        spin = listOf(),
        blocksi = listOf(BlocksiCategory.WebBasedEmail),
        generalCategory = GeneralCategory.Personal
    ), WebCategory(
        name = "Entertainment",
        description = "Sites that provide information about or promote motion pictures, non-news radio and television, music and programming guides, books, humor, comics, movie theatres, galleries, artists or review on entertainment, and magazines. Includes book sites that have personal flavor or extra-material by authors to promote the books.",
        spin = listOf(SpinWebCategory.EntertainmentAndArts),
        blocksi = listOf(BlocksiCategory.Entertainment),
        generalCategory = GeneralCategory.Personal
    ), WebCategory(
        name = "Arts and Culture",
        description = "Websites that cater to fine arts, cultural behaviors and backgrounds including conventions, artwork and paintings, music, languages, customs, etc. Also includes institutions such as museums, libraries and historic sites. Sites that promote historical, cultural heritage of certain area, but not purposely promoting travel.",
        spin = listOf(),
        blocksi = listOf(BlocksiCategory.ArtsAndCulture),
        generalCategory = GeneralCategory.Personal
    ), WebCategory(
        name = "Education",
        description = "Educational Institutions: Sites sponsored by schools, other educational facilities and non-academic research institutions, and sites that relate to educational events and activities. Educational Materials: Sites that provide information about, sell, or provide curriculum materials. Sites that direct instruction, as well as academic journals and similar publications where scholars and professors submit academic/research articles.",
        spin = listOf(),
        blocksi = listOf(BlocksiCategory.Education),
        generalCategory = GeneralCategory.Personal
    ), WebCategory(
        name = "Health,Medicine and Wellness",
        description = "Sites that provide information or advice on personal health or medical services, procedures, or devices, but not drugs. Includes self-help groups. This category includes cosmetic surgery providers, children's hospitals, but not sites of medical care for pets, which fall in Society and Lifestyle.",
        spin = listOf(SpinWebCategory.HealthAndMedicine),
        blocksi = listOf(BlocksiCategory.HealthAndWellness, BlocksiCategory.Medicine),
        generalCategory = GeneralCategory.Personal
    ), WebCategory(
        name = "Job Search",
        description = "Sites that offer information about or support the seeking of employment or employees. Includes career agents and consulting services that provide job postings.",
        spin = listOf(SpinWebCategory.JobSearch),
        blocksi = listOf(BlocksiCategory.JobSearch),
        generalCategory = GeneralCategory.Personal
    ), WebCategory(
        name = "News and Media",
        description = "Sites that offer current news and opinion, including those sponsored by newspapers, general-circulation magazines, or other media. This category includes TV and Radio sites, as long as they are not exclusively for entertainment purpose, but excludes academic journals. Alternative Journals: Online equivalents to supermarket tabloids and other fringe publications.",
        spin = listOf(),
        blocksi = listOf(BlocksiCategory.NewsAndMedia),
        generalCategory = GeneralCategory.Personal
    ), WebCategory(
        name = "Social Networking",
        description = "Includes websites that aid in the coordination of heterosexual relationships and companionship. Includes legal and non-sexual sites related to on-line dating, personal ads, dating services, clubs, etc.",
        spin = listOf(SpinWebCategory.SocialNetworking),
        blocksi = listOf(BlocksiCategory.SocialNetworking),
        generalCategory = GeneralCategory.Personal
    ), WebCategory(
        name = "Political Organizations",
        description = "Sites that are sponsored by or provide information about political parties and interest groups focused on elections or legislation. This is not to be confused with Government and Legal Organizations, and Advocacy Groups.",
        spin = listOf(),
        blocksi = listOf(BlocksiCategory.PoliticalOrganizations),
        generalCategory = GeneralCategory.Personal
    ), WebCategory(
        name = "Reference",
        description = "Websites that provide general reference data in the form of libraries, dictionaries, thesauri, encyclopedias, maps, directories, standards, etc.",
        spin = listOf(),
        blocksi = listOf(BlocksiCategory.Reference),
        generalCategory = GeneralCategory.Personal
    ), WebCategory(
        name = "Religion",
        description = "Sites that provide information about or promote Buddhism, Bahai, Christianity, Christian Science, Hinduism, Islam, Judaism, Mormonism, Shinto, and Sikhism, as well as atheism.",
        spin = listOf(SpinWebCategory.Religion),
        blocksi = listOf(BlocksiCategory.GlobalReligion),
        generalCategory = GeneralCategory.Personal
    ), WebCategory(
        name = "Shopping and Auction",
        description = "Websites that feature online promotion or sale of general goods and services such as electronics, flowers, jewelry, music, etc, excluding real estate. Also includes on-line auction services such as eBay, Amazon, Priceline.",
        spin = listOf(),
        blocksi = listOf(BlocksiCategory.ShoppingAndAuction),
        generalCategory = GeneralCategory.Personal
    ), WebCategory(
        name = "Fashion And Beauty",
        description = "Sites that focus on fashion stuffs and beauty products and techniques.",
        spin = listOf(SpinWebCategory.FashionAndBeauty),
        blocksi = listOf(),
        generalCategory = GeneralCategory.Personal
    ), WebCategory(
        name = "Society and Lifestyles",
        description = "This category contains sites that deal with everyday life issues and preferences such as passive hobbies (gardening, stamp collecting, pets), journals, blogs, etc.",
        spin = listOf(),
        blocksi = listOf(BlocksiCategory.SocietyAndLifestyles),
        generalCategory = GeneralCategory.Personal
    ), WebCategory(
        name = "Sports",
        description = "Includes sites that pertain to recreational sports and active hobbies such as fishing, hunting, jogging, canoeing, archery, chess, as well as organized, professional and competitive sports.",
        spin = listOf(),
        blocksi = listOf(BlocksiCategory.Sports),
        generalCategory = GeneralCategory.Personal
    ), WebCategory(
        name = "Travel",
        description = "Websites in this category feature travel related resources such as accommodations, transportation (rail, airlines, cruise ships), agencies, resort locations, tourist attractions, advisories, etc.",
        spin = listOf(),
        blocksi = listOf(BlocksiCategory.Travel),
        generalCategory = GeneralCategory.Personal
    ), WebCategory(
        name = "Personal Vehicles",
        description = "Websites that contain information on private use or sale of autos, boats, planes, motorcycles, etc., including parts and accessories.",
        spin = listOf(),
        blocksi = listOf(BlocksiCategory.PersonalVehicles),
        generalCategory = GeneralCategory.Personal
    ), WebCategory(
        name = "Dynamic Content",
        description = "URLs that are generated dynamically by a Web server.",
        spin = listOf(),
        blocksi = listOf(BlocksiCategory.DynamicContent),
        generalCategory = GeneralCategory.Personal
    ), WebCategory(
        name = "Meaningless Content",
        description = "This category houses URLs that cannot be definitively categorized due to lack of or ambiguous content.",
        spin = listOf(),
        blocksi = listOf(BlocksiCategory.MeaninglessContent),
        generalCategory = GeneralCategory.Personal
    ), WebCategory(
        name = "Folklore",
        description = "UFOs, fortune telling, horoscopes, fen shui, palm reading, tarot reading, and ghost stories.",
        spin = listOf(),
        blocksi = listOf(BlocksiCategory.Folklore),
        generalCategory = GeneralCategory.Personal
    ), WebCategory(
        name = "Web Chat and Instant Messaging",
        description = "Sites that host Web chat services, or that support or provide information about chat via HTTP or IRC.",
        spin = listOf(SpinWebCategory.InternetCommunications),
        blocksi = listOf(BlocksiCategory.WebChat, BlocksiCategory.InstantMessaging),
        generalCategory = GeneralCategory.Personal
    ), WebCategory(
        name = "Newsgroups and Message Boards",
        description = "Sites for online personal and business clubs, discussion groups, message boards, and list servers; includes 'blogs' and 'mail magazines.'",
        spin = listOf(),
        blocksi = listOf(BlocksiCategory.NewsgroupsAndMessageBoards),
        generalCategory = GeneralCategory.Personal
    ), WebCategory(
        name = "Digital Postcards",
        description = "Sites for sending/viewing digital post cards.",
        spin = listOf(),
        blocksi = listOf(BlocksiCategory.DigitalPostcards),
        generalCategory = GeneralCategory.Personal
    ), WebCategory(
        name = "Child Education",
        description = "Websites developed for children age 12 and under. Includes educational games, tools, organizations and schools. Note that children's hospitals are rated as Health.",
        spin = listOf(),
        blocksi = listOf(BlocksiCategory.ChildEducation),
        generalCategory = GeneralCategory.Personal
    ), WebCategory(
        name = "Real Estate",
        description = "Websites that promote the sale or renting of real estate properties.",
        spin = listOf(),
        blocksi = listOf(BlocksiCategory.RealEstate),
        generalCategory = GeneralCategory.Personal
    ), WebCategory(
        name = "Restaurant and Dining",
        description = "Websites related to restaurants and dining, includes locations, food reviews, recipes, catering services, etc.",
        spin = listOf(),
        blocksi = listOf(BlocksiCategory.RestaurantAndDining),
        generalCategory = GeneralCategory.Personal
    ), WebCategory(
        name = "Personal Websites and Blogs",
        description = "Private web pages that host personal information, opinions and ideas of the owners.",
        spin = listOf(SpinWebCategory.PersonalSitesAndBlogs),
        blocksi = listOf(BlocksiCategory.PersonalWebsitesAndBlogs),
        generalCategory = GeneralCategory.Personal
    ), WebCategory(
        name = "Content Servers",
        description = "Websites that host servers that distribute content for subscribing websites. Includes image and Web servers.",
        spin = listOf(),
        blocksi = listOf(BlocksiCategory.ContentServers),
        generalCategory = GeneralCategory.Personal
    ), WebCategory(
        name = "Domain Parking",
        description = "Sites that simply are place holders of domains without meaningful content.",
        spin = listOf(SpinWebCategory.ParkedDomains),
        blocksi = listOf(BlocksiCategory.DomainParking),
        generalCategory = GeneralCategory.Personal
    ), WebCategory(
        name = "Personal Privacy",
        description = "Sites providing online banking, trading, health care, and others that contain personal privacy information.",
        spin = listOf(),
        blocksi = listOf(BlocksiCategory.PersonalPrivacy),
        generalCategory = GeneralCategory.Personal
    ), WebCategory(
        name = "Unrated",
        description = "These websites have no particular category identified",
        spin = listOf(),
        blocksi = listOf(),
        generalCategory = GeneralCategory.Personal
    )
)

data class WebCategoryStatus(
    val category: WebCategory,
    val blocked: Boolean
)
