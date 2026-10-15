package com.historic.app.data

data class ProfileItem(val tag: String, val title: String, val body: String)

/** Biography content for Tun Dr Mahathir Mohamad: life story, contributions, criticism and legacy. */
object MahathirData {

    const val NAME = "Tun Dr Mahathir Mohamad"
    const val ROLE = "4th and 7th Prime Minister of Malaysia"

    const val INTRO =
        "A doctor from Alor Setar who became Malaysia's longest-serving prime minister (1981–2003) and, " +
            "at 92, returned to lead the country again (2018–2020). He is credited with pushing Malaysia toward " +
            "industrialisation and mega-projects, and criticised for concentrating power and restricting dissent. " +
            "Few figures have shaped modern Malaysia as much, and few are as debated."

    val facts: List<Pair<String, String>> = listOf(
        "Born" to "10 July 1925, Alor Setar, Kedah",
        "Trained as" to "Medical doctor, King Edward VII College of Medicine, Singapore",
        "Spouse" to "Tun Dr Siti Hasmah Mohd Ali (married 1956)",
        "Prime Minister" to "16 July 1981 – 31 Oct 2003 (4th)",
        "Prime Minister" to "10 May 2018 – 24 Feb 2020 (7th)",
        "Parties" to "UMNO, Bersatu, Pejuang",
        "Known for" to "Proton, Vision 2020, Petronas Twin Towers, Putrajaya, KLIA, Look East Policy",
        "Books" to "The Malay Dilemma (1970), A Doctor in the House (2011)",
    )

    val life: List<ProfileItem> = listOf(
        ProfileItem("1925", "Born in Alor Setar",
            "Mahathir bin Mohamad was born on 10 July 1925 in Alor Setar, Kedah, one of nine children of a schoolteacher " +
                "father, Mohamad Iskandar, and Wan Tempawan Wan Hanafi. He grew up in a modest household and attended " +
                "Sultan Abdul Hamid College."),
        ProfileItem("1942–1945", "The Japanese Occupation",
            "As a teenager during the Second World War he sold snacks, including banana fritters, to help the family. " +
                "He later described the occupation years, along with British colonial rule, as formative in shaping his " +
                "nationalism and his belief that Malays needed to become more self-reliant."),
        ProfileItem("1947–1953", "Medical studies in Singapore",
            "He studied at the King Edward VII College of Medicine in Singapore and qualified as a doctor. There he met " +
                "fellow student Siti Hasmah Mohd Ali, whom he married in 1956. He wrote political commentary while still a student."),
        ProfileItem("1950s", "Doctor and early activist",
            "He worked as a government medical officer and then ran his own clinic in Alor Setar, while becoming more active " +
                "in UMNO, the party he had joined as a young man after its founding in 1946."),
        ProfileItem("1964", "Enters Parliament",
            "He won the Kota Setar Selatan seat in the 1964 general election. He lost it in 1969."),
        ProfileItem("1969", "Expelled from UMNO",
            "After the 13 May 1969 racial riots, Mahathir wrote an open letter blaming Prime Minister Tunku Abdul Rahman's " +
                "leadership. UMNO expelled him. His exile from the party lasted until 1972."),
        ProfileItem("1970", "The Malay Dilemma",
            "He published his book arguing that Malays needed state support to compete economically. The government banned it, " +
                "and the ban was only lifted in 1981. Its ideas fed into the New Economic Policy."),
        ProfileItem("1974–1981", "Rise through the cabinet",
            "He returned to Parliament as MP for Kubang Pasu, became Education Minister in 1974, then Trade and Industry Minister " +
                "and, in 1976, Deputy Prime Minister under Tun Hussein Onn."),
        ProfileItem("1981", "Becomes Prime Minister",
            "When Hussein Onn retired for health reasons, Mahathir became the fourth Prime Minister on 16 July 1981. " +
                "He launched the Look East Policy in 1982, urging Malaysians to learn the work ethic of Japan and South Korea."),
        ProfileItem("1983–1985", "Proton and heavy industry",
            "He established Proton in 1983 as the national car company, and the first Proton Saga rolled out in 1985. " +
                "The Penang Bridge also opened in 1985. His government pushed heavy industries and privatised state enterprises."),
        ProfileItem("1987–1988", "Political crisis",
            "In 1987 he narrowly won the UMNO presidency against Tengku Razaleigh Hamzah. In October the government detained over " +
                "100 people under the Internal Security Act in Operation Lalang. In 1988 UMNO was declared illegal, he formed UMNO Baru, " +
                "and the Lord President of the Supreme Court, Salleh Abas, was removed."),
        ProfileItem("1991", "Vision 2020",
            "He unveiled Wawasan 2020, a goal for Malaysia to become a fully developed nation by 2020 and reach high-income status."),
        ProfileItem("1996–1999", "Mega-projects",
            "The Petronas Twin Towers were completed in 1996 and were the world's tallest buildings until 2004. The Multimedia Super " +
                "Corridor, KL International Airport (1998), Putrajaya, the Commonwealth Games (1998) and the Sepang F1 circuit (1999) followed."),
        ProfileItem("1997–1998", "Asian financial crisis",
            "He rejected the IMF approach, blamed currency speculators, imposed capital controls and pegged the ringgit at 3.80 to the US dollar in " +
                "September 1998. Economists still debate how much these measures helped recovery."),
        ProfileItem("1998", "Anwar Ibrahim sacked",
            "On 2 September 1998 he dismissed his deputy, Anwar Ibrahim, who was then arrested and later convicted of corruption and sodomy " +
                "in trials widely criticised at home and abroad. The case sparked the Reformasi movement."),
        ProfileItem("2003", "Retires after 22 years",
            "He handed over to Abdullah Ahmad Badawi on 31 October 2003, ending the longest premiership in Malaysia's history."),
        ProfileItem("2008–2016", "Critic from outside",
            "He left UMNO in 2008 in protest at Badawi, rejoined in 2009, then quit again in 2016 over the 1MDB scandal under Najib Razak. " +
                "That year he co-founded the Malay-based party Bersatu."),
        ProfileItem("2018", "The comeback",
            "Leading the Pakatan Harapan coalition, he defeated Barisan Nasional on 9 May 2018, ending 61 years of one-coalition rule. " +
                "Aged 92, he was sworn in on 10 May as the world's oldest serving head of government, and Anwar was pardoned days later."),
        ProfileItem("2018–2019", "Second term reforms",
            "His government abolished the GST, reopened 1MDB investigations, and in 2019 passed constitutional amendments lowering the voting age to 18 (Undi18)."),
        ProfileItem("2020", "Resignation and Sheraton Move",
            "A split within the coalition led to his resignation on 24 February 2020 and the fall of the government. After months of negotiation " +
                "Muhyiddin Yassin became Prime Minister on 1 March. Mahathir later founded the Pejuang party."),
        ProfileItem("2022", "Loses his seat",
            "In the November 2022 election he lost his Langkawi seat and his deposit for the first time in his career."),
        ProfileItem("2025–2026", "A centenarian statesman",
            "He turned 100 on 10 July 2025 and, according to news reports, at age 101 attended the National Day celebrations in Putrajaya on 31 August 2026."),
    )

    val contributions: List<ProfileItem> = listOf(
        ProfileItem("Industry", "From commodities to manufacturing",
            "His government pushed Malaysia beyond tin, rubber and palm oil toward manufacturing, electronics and heavy industry. " +
                "Proton (1983) and Perodua (1993) built a national car industry, though the cars relied heavily on protection."),
        ProfileItem("Economy", "Sustained growth",
            "During his first term, the economy grew by roughly 6–7% a year on average, helped by foreign investment, infrastructure and the 1980s oil revenues. " +
                "Poverty fell and a larger middle class emerged."),
        ProfileItem("Policy", "Look East and privatisation",
            "The Look East Policy sent students and workers to Japan and Korea. Privatisation of state utilities and roads, such as the North–South Expressway, " +
                "brought private capital into infrastructure."),
        ProfileItem("Landmarks", "Symbols of a modern Malaysia",
            "The Petronas Twin Towers, KLIA, Putrajaya, Cyberjaya and the Sepang circuit became national symbols and put Kuala Lumpur on the world map."),
        ProfileItem("Vision", "Vision 2020",
            "Wawasan 2020 gave the country a long-term goal. Malaysia did not reach high-income status by 2020, but it remained an upper-middle-income economy " +
                "and the target shaped planning for three decades."),
        ProfileItem("Crisis", "Handling the 1997–98 crisis",
            "His capital controls and ringgit peg went against the IMF's advice. Supporters say they protected the economy and helped it rebound quickly; critics " +
                "argue that other countries recovered without them and that the measures protected politically connected firms."),
        ProfileItem("Diplomacy", "A voice for the developing world",
            "He championed South–South cooperation, the Non-Aligned Movement and Muslim-world causes, and criticised Western dominance in global finance. " +
                "Malaysia also contributed peacekeepers to missions such as Somalia and Bosnia in the 1990s."),
        ProfileItem("Public service", "Civil service reforms",
            "He introduced the 'Clean, Efficient and Trustworthy' campaign, with name tags, punch-clocks and a Client's Charter, to make government more disciplined."),
        ProfileItem("Education", "Universities and technology",
            "The International Islamic University Malaysia was founded in 1983, and the Multimedia Super Corridor aimed to build a technology and IT hub."),
        ProfileItem("2018–2020", "Second term",
            "Abolishing the GST, probing 1MDB, lowering the voting age to 18 and presiding over Malaysia's first change of federal government since independence."),
    )

    val criticisms: List<ProfileItem> = listOf(
        ProfileItem("1985", "Memali incident",
            "A police operation in Memali, Baling, Kedah on 19 November 1985 against followers of the preacher Ibrahim Libya left 14 villagers and four policemen dead. " +
                "Critics continue to demand a fuller public inquiry."),
        ProfileItem("1987", "Operation Lalang",
            "More than 100 activists, opposition politicians and others were detained without trial under the Internal Security Act and several newspapers were " +
                "suspended. The government cited the risk of racial violence."),
        ProfileItem("1988", "Judicial crisis",
            "The removal of Lord President Salleh Abas and two Supreme Court judges is widely regarded as a blow to judicial independence."),
        ProfileItem("1998", "Anwar's dismissal and trials",
            "The dismissal and prosecution of his deputy led to mass protests and lasting questions about fairness, even though Anwar was eventually freed and later became Prime Minister."),
        ProfileItem("Power", "Concentration of power",
            "Critics say that during his first tenure the executive grew stronger at the expense of Parliament, the judiciary, the media and the royal institution."),
        ProfileItem("Business", "Cronyism and bailouts",
            "Large contracts and privatisations went to well-connected businessmen, and in the 1990s the government bailed out several debt-laden companies."),
        ProfileItem("Race", "Race-based politics",
            "He defended affirmative action for Bumiputera as essential. Critics argue it deepened ethnic divisions and benefited a narrow elite."),
        ProfileItem("2003", "OIC speech",
            "Remarks about Jews at the Organisation of Islamic Cooperation summit in October 2003 were widely condemned as antisemitic."),
        ProfileItem("2020", "Succession and the Sheraton Move",
            "Delays in handing power to Anwar, and the resignation that triggered the government's collapse, left political instability and a succession of three prime ministers in under four years."),
    )

    val legacy: List<String> = listOf(
        "Supporters see the architect of modern Malaysia: a leader who built industry, infrastructure and national confidence, and stood up to outside pressure.",
        "Critics see a strongman whose methods weakened institutions and whose policies entrenched patronage and ethnic politics.",
        "Historians generally agree on one thing: he is among the most consequential Malaysian leaders since independence, whose years in power span more than two decades of the country's history.",
    )

    const val SOURCES_NOTE =
        "Compiled from public reporting and historical records (Perdana Leadership Foundation, Business Today, Fulcrum, New Straits Times). " +
            "Verify details before citing."
}
