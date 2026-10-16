package com.historic.app.data

enum class FigureEra(val label: String) {
    PRE("Before WWI"), WW1("World War I"), WW2("World War II"), COLD("Cold War"), MALAYSIA("Malaysia")
}

/** Used only to pick an accent colour for the illustration. */
enum class Camp { ALLIES, AXIS, SOVIET, MALAYSIA, OTHER }

enum class Headwear { NONE, PEAKED_CAP, SPIKED_HELMET, HOMBURG, SONGKOK, OFFICER_CAP, FLAT_CAP }
enum class Attire { SUIT, UNIFORM, TUNIC }

data class Figure(
    val id: String,
    val name: String,
    val years: String,
    val role: String,
    val affiliation: String,
    val camp: Camp,
    val era: FigureEra,
    val headwear: Headwear,
    val attire: Attire,
    val summary: String,
    val highlights: List<String>,
    val criticism: String? = null,
    val events: List<String> = emptyList(),
)

private fun fig(
    id: String, name: String, years: String, role: String, affiliation: String,
    camp: Camp, era: FigureEra, headwear: Headwear, attire: Attire,
    summary: String, highlights: List<String>, criticism: String? = null, events: List<String> = emptyList(),
) = Figure(id, name, years, role, affiliation, camp, era, headwear, attire, summary, highlights, criticism, events)

object FigureData {

    val all: List<Figure> = listOf(

        // ---------------- Before and during WWI ----------------
        fig("bismarck", "Otto von Bismarck", "1815–1898", "First Chancellor of the German Empire", "Germany",
            Camp.OTHER, FigureEra.PRE, Headwear.SPIKED_HELMET, Attire.UNIFORM,
            "The Prussian statesman who unified Germany through three wars and then spent two decades trying to keep Europe at peace.",
            listOf(
                "Became Minister-President of Prussia in 1862 and won wars against Denmark (1864), Austria (1866) and France (1870–71).",
                "The German Empire was proclaimed at Versailles on 18 January 1871, with Bismarck as its Chancellor until 1890.",
                "Built a web of alliances to isolate France and hosted the 1878 Congress of Berlin.",
                "Introduced pioneering health, accident and old-age insurance in the 1880s.",
                "Dismissed by the young Kaiser Wilhelm II in March 1890.",
            ),
            "His anti-Catholic Kulturkampf and the anti-socialist laws of 1878 persecuted minorities and political opponents. After he left office, his careful alliance system unravelled.",
            listOf("ww1-sarajevo", "ww1-belgium")),

        fig("wilhelm2", "Kaiser Wilhelm II", "1859–1941", "German Emperor and King of Prussia", "German Empire",
            Camp.AXIS, FigureEra.WW1, Headwear.SPIKED_HELMET, Attire.UNIFORM,
            "The last German Emperor, who led his country into the First World War and lost his throne in its aftermath.",
            listOf(
                "Became Emperor in 1888 and dismissed Bismarck two years later.",
                "Pursued a large navy, a rivalry that soured relations with Britain.",
                "In July 1914 gave Austria-Hungary strong backing after Sarajevo.",
                "Abdicated in November 1918 and lived in exile in the Netherlands until his death in 1941.",
            ),
            "Historians debate how far his erratic diplomacy and bellicose speeches pushed Europe toward war, and how much power the generals held by 1914.",
            listOf("ww1-belgium", "ww1-armistice")),

        fig("lenin", "Vladimir Lenin", "1870–1924", "Bolshevik leader, founder of Soviet Russia", "Russia / USSR",
            Camp.SOVIET, FigureEra.WW1, Headwear.FLAT_CAP, Attire.SUIT,
            "The revolutionary who led the Bolsheviks to power in 1917 and took Russia out of the First World War.",
            listOf(
                "Returned to Russia from exile in April 1917 and called for peace and 'all power to the Soviets'.",
                "The Bolsheviks seized power in November 1917 (October in the old calendar).",
                "Signed the Treaty of Brest-Litovsk with Germany in March 1918.",
                "Won the Civil War, introduced the New Economic Policy (1921) and saw the USSR formed in 1922.",
                "Died on 21 January 1924 after a series of strokes.",
            ),
            "His government used the Red Terror and the Cheka secret police against opponents, and banned other parties.",
            listOf("ww1-russia", "ww1-brest")),

        fig("wilson", "Woodrow Wilson", "1856–1924", "28th President of the United States", "United States",
            Camp.ALLIES, FigureEra.WW1, Headwear.NONE, Attire.SUIT,
            "The US president who kept America neutral until 1917, then led it to war and proposed a new world order.",
            listOf(
                "Asked Congress to declare war on Germany in April 1917.",
                "Set out his Fourteen Points for peace in January 1918, including self-determination and a League of Nations.",
                "Won the 1919 Nobel Peace Prize, but the US Senate refused to ratify the Versailles Treaty or join the League.",
            ),
            "His administration expanded racial segregation in the federal workforce, and his ideals of self-determination were applied unevenly to colonised peoples.",
            listOf("ww1-us", "ww1-versailles")),

        // ---------------- World War II ----------------
        fig("churchill", "Winston Churchill", "1874–1965", "Prime Minister of the United Kingdom", "United Kingdom",
            Camp.ALLIES, FigureEra.WW2, Headwear.HOMBURG, Attire.SUIT,
            "Britain's wartime leader, whose speeches and refusal to negotiate rallied the country after the fall of France.",
            listOf(
                "Became Prime Minister on 10 May 1940, promising 'blood, toil, tears and sweat'.",
                "Led Britain through the Battle of Britain and the Blitz and built the alliance with the US and USSR.",
                "Lost the July 1945 general election, but returned as Prime Minister in 1951–55.",
                "Warned of an 'iron curtain' across Europe in a March 1946 speech in Fulton, Missouri.",
                "Won the Nobel Prize in Literature in 1953.",
            ),
            "Critics point to his imperial views, to policy during the 1943 Bengal famine, and to the fall of Singapore in 1942 under his government.",
            listOf("ww2-dunkirk", "ww2-britain", "ww2-dday", "mal-singapore")),

        fig("roosevelt", "Franklin D. Roosevelt", "1882–1945", "32nd President of the United States", "United States",
            Camp.ALLIES, FigureEra.WW2, Headwear.NONE, Attire.SUIT,
            "The only US president elected four times, who led America out of the Depression and through most of the Second World War.",
            listOf(
                "Launched the New Deal in 1933 to fight the Great Depression.",
                "Backed Britain with Lend-Lease from 1941 and signed the Atlantic Charter with Churchill.",
                "Called 7 December 1941 'a date which will live in infamy' and took the US to war.",
                "Met Churchill and Stalin at Tehran (1943) and Yalta (February 1945).",
                "Died on 12 April 1945, weeks before Germany surrendered.",
            ),
            "In February 1942 he signed the order that led to the forced removal and internment of about 120,000 Japanese Americans.",
            listOf("ww2-pearl", "ww2-midway", "ww2-dday")),

        fig("stalin", "Joseph Stalin", "1878–1953", "Leader of the Soviet Union", "Soviet Union",
            Camp.SOVIET, FigureEra.WW2, Headwear.NONE, Attire.TUNIC,
            "The dictator who industrialised the USSR at enormous human cost, defeated Nazi Germany alongside the Allies, and then opposed the West in the Cold War.",
            listOf(
                "Rose to lead the Communist Party after Lenin's death and ruled until 1953.",
                "Signed the Nazi–Soviet Pact in August 1939 and invaded eastern Poland that September.",
                "Was caught off guard by the German invasion of June 1941 but led the Soviet war effort to Stalingrad and Berlin.",
                "Attended the Tehran, Yalta and Potsdam conferences that shaped postwar Europe.",
                "Oversaw the Berlin Blockade and the first Soviet atomic test in 1949.",
            ),
            "Collectivisation, the 1932–33 famine, the Great Purge and the Gulag caused millions of deaths. Whole peoples were deported during the war.",
            listOf("ww2-barbarossa", "ww2-stalingrad", "ww2-berlin", "cw-airlift", "cw-soviet-bomb")),

        fig("hitler", "Adolf Hitler", "1889–1945", "Dictator of Nazi Germany", "Nazi Germany",
            Camp.AXIS, FigureEra.WW2, Headwear.NONE, Attire.UNIFORM,
            "The Austrian-born leader of the Nazi Party whose aggression started the Second World War in Europe and whose regime carried out the Holocaust.",
            listOf(
                "Became Chancellor on 30 January 1933 and soon made himself dictator.",
                "Invaded Poland on 1 September 1939, then conquered much of Europe and attacked the Soviet Union in 1941.",
                "His regime murdered about six million Jews in the Holocaust, along with millions of others including Roma, disabled people and Soviet prisoners.",
                "Died by suicide in Berlin on 30 April 1945 as Soviet forces closed in.",
            ),
            "Included so the app tells the full history. The Nazi regime is remembered for genocide and aggression, and is not glorified here.",
            listOf("ww2-poland", "ww2-barbarossa", "ww2-auschwitz", "ww2-berlin")),

        fig("mussolini", "Benito Mussolini", "1883–1945", "Fascist dictator of Italy", "Italy",
            Camp.AXIS, FigureEra.WW2, Headwear.NONE, Attire.UNIFORM,
            "The founder of Fascism, who ruled Italy for two decades and tied it to Hitler's Germany.",
            listOf(
                "Came to power in 1922 and became dictator by the mid-1920s.",
                "Invaded Ethiopia in 1935–36 and joined Germany in the Pact of Steel (1939).",
                "Entered the war on 10 June 1940 as France collapsed.",
                "Was deposed in July 1943, then led a German-backed puppet state in the north; he was killed by partisans on 28 April 1945.",
            ),
            "Italian forces used poison gas in Ethiopia, and his regime's racial laws of 1938 persecuted Jews.",
            listOf("ww2-france", "ww2-alamein")),

        fig("hirohito", "Emperor Hirohito", "1901–1989", "Shōwa Emperor of Japan", "Japan",
            Camp.AXIS, FigureEra.WW2, Headwear.OFFICER_CAP, Attire.UNIFORM,
            "Japan's emperor during the Pacific War, whose radio broadcast announced the surrender.",
            listOf(
                "Reigned from 1926 to 1989, the longest reign of any Japanese emperor.",
                "Was the nominal head of the armed forces when Japan attacked Pearl Harbor and Malaya in December 1941.",
                "Announced Japan's surrender in a radio address on 15 August 1945.",
                "Renounced his divinity in 1946 and stayed on as a symbolic monarch.",
            ),
            "Historians still debate how much real influence he had over wartime decisions, and the Allies chose not to put him on trial.",
            listOf("ww2-pearl", "ww2-hiroshima", "ww2-surrender")),

        fig("tojo", "Hideki Tōjō", "1884–1948", "Prime Minister of Japan, 1941–44", "Japan",
            Camp.AXIS, FigureEra.WW2, Headwear.OFFICER_CAP, Attire.UNIFORM,
            "The army general who headed Japan's government when it went to war with the US, Britain and the Netherlands.",
            listOf(
                "Became Prime Minister in October 1941 while remaining army minister.",
                "His government approved the attacks on Pearl Harbor and Malaya.",
                "Resigned in July 1944 after the fall of Saipan.",
                "Was convicted of war crimes by the Tokyo tribunal and hanged on 23 December 1948.",
            ),
            null,
            listOf("ww2-pearl", "mal-kotabharu")),

        fig("degaulle", "Charles de Gaulle", "1890–1970", "Leader of Free France, later President", "France",
            Camp.ALLIES, FigureEra.WW2, Headwear.OFFICER_CAP, Attire.UNIFORM,
            "The general who refused to accept France's defeat in 1940 and later rebuilt the French state.",
            listOf(
                "Broadcast his appeal from London on 18 June 1940 urging the French to keep fighting.",
                "Headed the Free French and the provisional government after liberation in 1944.",
                "Returned to power in 1958, founded the Fifth Republic and was president until 1969.",
                "Ended the Algerian War through the 1962 independence of Algeria.",
            ),
            null,
            listOf("ww2-france", "ww2-dday")),

        fig("eisenhower", "Dwight D. Eisenhower", "1890–1969", "Supreme Allied Commander, later US President", "United States",
            Camp.ALLIES, FigureEra.WW2, Headwear.OFFICER_CAP, Attire.UNIFORM,
            "The general who commanded the D-Day invasion and later served two terms as US president.",
            listOf(
                "Was Supreme Allied Commander in Europe from 1943.",
                "Gave the order for the Normandy landings on 6 June 1944.",
                "Served as president from 1953 to 1961, ending the Korean War and warning of a 'military-industrial complex' in his farewell speech.",
            ),
            null,
            listOf("ww2-dday", "cw-korea")),

        fig("yamashita", "Tomoyuki Yamashita", "1885–1946", "Japanese general, 'Tiger of Malaya'", "Japan",
            Camp.AXIS, FigureEra.WW2, Headwear.OFFICER_CAP, Attire.UNIFORM,
            "The commander of the Japanese 25th Army, who conquered Malaya and Singapore in about 70 days.",
            listOf(
                "Landed at Kota Bharu on 8 December 1941 and drove south through the peninsula.",
                "Accepted the British surrender of Singapore on 15 February 1942.",
                "Later commanded forces in the Philippines.",
                "Was convicted by a US military commission in Manila for atrocities committed by troops under his command and hanged in February 1946.",
            ),
            "The principle of command responsibility used in his trial is still debated by legal historians.",
            listOf("mal-kotabharu", "mal-jitra", "mal-singapore")),

        fig("percival", "Arthur Percival", "1887–1966", "British Lieutenant-General, GOC Malaya", "United Kingdom",
            Camp.ALLIES, FigureEra.WW2, Headwear.OFFICER_CAP, Attire.UNIFORM,
            "The British commander who surrendered Singapore to Japan on 15 February 1942.",
            listOf(
                "Took command in Malaya in 1941 with limited tanks and aircraft.",
                "Signed the surrender of about 80,000 troops at the Ford Factory in Bukit Timah.",
                "Spent over three years as a prisoner of war and was present at the Japanese surrender in Tokyo Bay in September 1945.",
            ),
            "Often blamed for the defeat. Historians stress the weak air power, the lack of naval support and earlier strategic decisions in London.",
            listOf("mal-jitra", "mal-singapore")),

        // ---------------- Cold War ----------------
        fig("truman", "Harry S. Truman", "1884–1972", "33rd President of the United States", "United States",
            Camp.ALLIES, FigureEra.COLD, Headwear.HOMBURG, Attire.SUIT,
            "The president who ended the Second World War and set the course of US policy at the start of the Cold War.",
            listOf(
                "Became president when Roosevelt died in April 1945 and authorised the atomic bombings of Hiroshima and Nagasaki.",
                "Announced the Truman Doctrine (1947), the Marshall Plan and the Berlin Airlift.",
                "Helped found NATO in 1949 and sent US forces to Korea in 1950.",
            ),
            "The decision to use atomic weapons remains one of the most debated in history.",
            listOf("ww2-hiroshima", "cw-truman", "cw-marshall", "cw-airlift", "cw-nato", "cw-korea")),

        fig("khrushchev", "Nikita Khrushchev", "1894–1971", "Soviet leader, 1953–1964", "Soviet Union",
            Camp.SOVIET, FigureEra.COLD, Headwear.NONE, Attire.SUIT,
            "The Soviet leader who denounced Stalin, launched the Space Race and faced Kennedy over Cuba.",
            listOf(
                "Delivered the 'secret speech' in 1956 criticising Stalin's crimes.",
                "Oversaw the launch of Sputnik in 1957 and Yuri Gagarin's flight in 1961.",
                "Backed the Berlin Wall (1961) and placed missiles in Cuba (1962).",
                "Was removed from power by his colleagues in October 1964.",
            ),
            "Ordered Soviet tanks into Hungary in 1956.",
            listOf("cw-hungary", "cw-sputnik", "cw-wall-built", "cw-cuba")),

        fig("kennedy", "John F. Kennedy", "1917–1963", "35th President of the United States", "United States",
            Camp.ALLIES, FigureEra.COLD, Headwear.NONE, Attire.SUIT,
            "The young president who faced the Berlin Wall crisis and the Cuban Missile Crisis, and was assassinated in 1963.",
            listOf(
                "Took office in January 1961 and approved the failed Bay of Pigs invasion that April.",
                "Managed the thirteen-day Cuban Missile Crisis in October 1962 and agreed a deal to withdraw missiles.",
                "Signed the Partial Nuclear Test Ban Treaty in 1963.",
                "Was assassinated in Dallas on 22 November 1963.",
            ),
            "His government also deepened US involvement in South Vietnam.",
            listOf("cw-wall-built", "cw-cuba", "cw-danang")),

        fig("mao", "Mao Zedong", "1893–1976", "Chairman of the Chinese Communist Party", "China",
            Camp.SOVIET, FigureEra.COLD, Headwear.FLAT_CAP, Attire.TUNIC,
            "The revolutionary who led the Communist victory in China and ruled the People's Republic for 27 years.",
            listOf(
                "Proclaimed the People's Republic of China on 1 October 1949.",
                "Sent Chinese forces into the Korean War in 1950.",
                "Hosted US President Nixon in Beijing in February 1972.",
                "Died on 9 September 1976.",
            ),
            "The Great Leap Forward (1958–62) led to a catastrophic famine killing tens of millions, and the Cultural Revolution (1966–76) brought violent persecution and turmoil.",
            listOf("cw-prc", "cw-korea", "cw-nixon-china")),

        fig("hochiminh", "Ho Chi Minh", "1890–1969", "Leader of the Vietnamese independence movement", "Vietnam",
            Camp.SOVIET, FigureEra.COLD, Headwear.NONE, Attire.TUNIC,
            "The nationalist and communist who led Vietnam's fight against French rule and later the war with the US-backed South.",
            listOf(
                "Declared Vietnamese independence in Hanoi on 2 September 1945.",
                "His Viet Minh defeated the French at Dien Bien Phu in 1954.",
                "Served as president of North Vietnam until his death in 1969, six years before reunification.",
            ),
            "His government carried out harsh land reform and political repression in the 1950s.",
            listOf("cw-danang", "cw-saigon")),

        fig("castro", "Fidel Castro", "1926–2016", "Leader of Cuba", "Cuba",
            Camp.SOVIET, FigureEra.COLD, Headwear.FLAT_CAP, Attire.UNIFORM,
            "The revolutionary who overthrew Cuba's government in 1959 and ruled for almost five decades.",
            listOf(
                "Led the Cuban Revolution that took power in January 1959.",
                "Aligned Cuba with the Soviet Union and hosted Soviet missiles in 1962.",
                "Remained leader until handing over to his brother Raúl in 2008.",
            ),
            "His government jailed political opponents and restricted free speech, while praised for literacy and health programmes.",
            listOf("cw-cuba")),

        fig("reagan", "Ronald Reagan", "1911–2004", "40th President of the United States", "United States",
            Camp.ALLIES, FigureEra.COLD, Headwear.NONE, Attire.SUIT,
            "The former actor and governor who led the US in the final decade of the Cold War.",
            listOf(
                "Expanded US defence spending and pressed the Soviet Union on arms control.",
                "Urged Gorbachev to 'tear down this wall' at the Brandenburg Gate in June 1987.",
                "Signed the INF Treaty with Gorbachev in December 1987.",
            ),
            null,
            listOf("cw-inf", "cw-wall-fall")),

        fig("gorbachev", "Mikhail Gorbachev", "1931–2022", "Last leader of the Soviet Union", "Soviet Union",
            Camp.SOVIET, FigureEra.COLD, Headwear.NONE, Attire.SUIT,
            "The reformer whose policies of openness and restructuring helped end the Cold War and, unintentionally, the USSR.",
            listOf(
                "Became General Secretary in 1985 and introduced glasnost and perestroika.",
                "Signed the INF Treaty and pulled Soviet troops out of Afghanistan.",
                "Did not use force when Eastern European communist governments fell in 1989.",
                "Won the Nobel Peace Prize in 1990 and resigned on 25 December 1991.",
            ),
            "Many in Russia blame his reforms for the Soviet collapse and the hardships of the 1990s.",
            listOf("cw-inf", "cw-wall-fall", "cw-ussr-end")),

        // ---------------- Malaysia ----------------
        fig("adnan", "Lt Adnan Saidi", "1915–1942", "Officer, 1st Battalion Malay Regiment", "Malaya",
            Camp.MALAYSIA, FigureEra.MALAYSIA, Headwear.SONGKOK, Attire.UNIFORM,
            "A Malay Regiment lieutenant remembered for the defence of Bukit Chandu during the Battle of Singapore.",
            listOf(
                "Born in Selangor and commissioned into the Malay Regiment.",
                "Led his platoon on the Pasir Panjang Ridge on 12–14 February 1942 against much larger Japanese forces.",
                "Was killed on 14 February 1942, a day before Singapore surrendered.",
                "Remembered as a symbol of courage and loyalty in Malaysia and Singapore.",
            ),
            null,
            listOf("mal-rmr", "mal-pasirpanjang")),

        fig("limboseng", "Lim Bo Seng", "1909–1944", "Resistance leader, Force 136", "Malaya",
            Camp.MALAYSIA, FigureEra.MALAYSIA, Headwear.NONE, Attire.SUIT,
            "A Chinese businessman who became a leader of anti-Japanese resistance with Britain's Force 136.",
            listOf(
                "Organised resistance and supply networks among overseas Chinese.",
                "Entered Malaya with Force 136 to coordinate with guerrillas.",
                "Was captured by the Japanese in 1944 and died in Batu Gajah prison in June 1944.",
            ),
            null,
            listOf("mal-mpaja")),

        fig("onn", "Dato' Onn Jaafar", "1895–1962", "Founder of UMNO", "Malaya",
            Camp.MALAYSIA, FigureEra.MALAYSIA, Headwear.SONGKOK, Attire.SUIT,
            "The Johor leader who united Malay organisations against the Malayan Union and founded UMNO.",
            listOf(
                "Led the 1946 Malay Congress that formed UMNO and became its first president.",
                "Mobilised Malay opposition, forcing Britain to drop the Malayan Union.",
                "Left UMNO in 1951 to form the multi-racial Independence of Malaya Party, which failed to win wide support.",
            ),
            null,
            listOf("mal-umno")),

        fig("tunku", "Tunku Abdul Rahman", "1903–1990", "First Prime Minister of Malaya and Malaysia", "Malaysia",
            Camp.MALAYSIA, FigureEra.MALAYSIA, Headwear.SONGKOK, Attire.SUIT,
            "'Bapa Kemerdekaan', the father of independence, who led Malaya to Merdeka and brought Malaysia into being.",
            listOf(
                "Became UMNO president in 1951 and led the Alliance coalition to victory in 1955.",
                "Met communist leader Chin Peng at the Baling Talks in 1955.",
                "Proclaimed independence at Stadium Merdeka on 31 August 1957.",
                "Formed Malaysia in 1963 and resigned in 1970 after the May 1969 riots.",
                "Served as the first Secretary-General of the Organisation of the Islamic Conference.",
            ),
            null,
            listOf("mal-baling", "mal-merdeka", "mal-malaysia", "mal-konfrontasi")),

        fig("razak", "Tun Abdul Razak", "1922–1976", "Second Prime Minister of Malaysia", "Malaysia",
            Camp.MALAYSIA, FigureEra.MALAYSIA, Headwear.SONGKOK, Attire.SUIT,
            "'Bapa Pembangunan', the father of development, who steered Malaysia after 1969 and launched the New Economic Policy.",
            listOf(
                "Led rural development and FELDA land schemes in the 1950s and 60s.",
                "Headed the National Operations Council after the 13 May 1969 riots and became Prime Minister in 1970.",
                "Launched the New Economic Policy in 1971 and established diplomatic ties with China in 1974.",
                "Died in London on 14 January 1976.",
            ),
            "The NEP's race-based preferences remain a subject of political debate.",
            listOf("mal-konfrontasi", "mal-asean")),

        fig("mahathir", "Tun Dr Mahathir Mohamad", "b. 1925", "4th and 7th Prime Minister of Malaysia", "Malaysia",
            Camp.MALAYSIA, FigureEra.MALAYSIA, Headwear.SONGKOK, Attire.SUIT,
            "Malaysia's longest-serving prime minister (1981–2003), who returned to office at 92 in 2018.",
            listOf(
                "Trained as a doctor in Singapore before entering politics through UMNO.",
                "Founded Proton in 1983 and launched Vision 2020 in 1991.",
                "Presided over the Petronas Twin Towers, KLIA and Putrajaya.",
                "Dismissed Anwar Ibrahim in 1998 and returned to lead the 2018 election victory over Barisan Nasional.",
            ),
            "His tenure is praised for development and criticised for the concentration of power and restricting dissent. See his full biography for the details.",
            listOf("mal-asean", "mal-hatyai")),

        fig("chinpeng", "Chin Peng", "1924–2013", "Secretary-General of the Malayan Communist Party", "Malaya",
            Camp.MALAYSIA, FigureEra.MALAYSIA, Headwear.FLAT_CAP, Attire.TUNIC,
            "The communist leader who fought alongside the British against Japan, then led the armed uprising against British rule.",
            listOf(
                "Led the MPAJA's resistance against the Japanese and received British honours in 1945.",
                "Became head of the Malayan Communist Party in 1947 and commanded the guerrillas during the Emergency.",
                "Met Tunku Abdul Rahman at the Baling Talks in 1955.",
                "Signed the Hat Yai Peace Accords in 1989 and died in Bangkok in 2013.",
            ),
            "The insurgency cost many civilian lives, and Malaysia refused to let him return home. His legacy is still contested.",
            listOf("mal-mpaja", "mal-emergency", "mal-baling", "mal-hatyai")),

        fig("leekuanyew", "Lee Kuan Yew", "1923–2015", "First Prime Minister of Singapore", "Singapore",
            Camp.MALAYSIA, FigureEra.MALAYSIA, Headwear.NONE, Attire.SUIT,
            "The leader who took Singapore into Malaysia in 1963 and then out of it in 1965, and built the city-state's economy.",
            listOf(
                "Became Prime Minister of self-governing Singapore in 1959.",
                "Campaigned for the merger with Malaysia in 1963.",
                "Led Singapore as an independent republic from 9 August 1965 until stepping down in 1990.",
            ),
            "Praised for development, criticised for limiting political opposition and the press.",
            listOf("mal-malaysia", "mal-singapore-sep")),

        fig("sukarno", "Sukarno", "1901–1970", "First President of Indonesia", "Indonesia",
            Camp.OTHER, FigureEra.MALAYSIA, Headwear.SONGKOK, Attire.SUIT,
            "Indonesia's independence leader, who opposed the formation of Malaysia in the Konfrontasi of 1963–66.",
            listOf(
                "Proclaimed Indonesian independence on 17 August 1945.",
                "Hosted the 1955 Bandung Conference of Asian and African nations.",
                "Called Malaysia a neo-colonial project, leading to the Konfrontasi, and lost power after the turmoil of 1965–67.",
            ),
            null,
            listOf("mal-konfrontasi")),
    )

    val byId: Map<String, Figure> = all.associateBy { it.id }
}
