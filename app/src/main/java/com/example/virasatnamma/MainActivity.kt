package com.example.virasatnamma

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.virasatnamma.model.HERITAGE_SITES
import com.example.virasatnamma.model.Site
import com.example.virasatnamma.ui.theme.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            VirasatNammaApp()
        }
    }
}

@Composable
fun VirasatNammaApp() {
    var currentScreen by remember { mutableStateOf("login") }
    var selectedTab by remember { mutableStateOf("home") }
    var selectedSite by remember { mutableStateOf<Site?>(null) }
    val sites = remember { mutableStateListOf(*HERITAGE_SITES.toTypedArray()) }
    var username by remember { mutableStateOf("") }

    MaterialTheme {
        Surface(color = DarkBrown, modifier = Modifier.fillMaxSize()) {
            Column {
                // Saffron Top Border
                Box(modifier = Modifier.fillMaxWidth().height(6.dp).background(Saffron))

                when (currentScreen) {
                    "login" -> LoginScreen(onLogin = { name -> 
                        username = name
                        currentScreen = "main" 
                    })
                    "main" -> MainContainer(
                        username = username,
                        selectedTab = selectedTab,
                        sites = sites,
                        onTabSelect = { selectedTab = it },
                        onSiteSelect = { 
                            selectedSite = it
                            currentScreen = "detail"
                        }
                    )
                    "detail" -> DetailScreen(
                        site = selectedSite!!,
                        onBack = { currentScreen = "main" },
                        onCheckIn = { id ->
                            val index = sites.indexOfFirst { it.id == id }
                            if (index != -1) {
                                sites[index] = sites[index].copy(checkedIn = !sites[index].checkedIn)
                                selectedSite = sites[index]
                            }
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun LoginScreen(onLogin: (String) -> Unit) {
    var name by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize().padding(28.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("🪔", fontSize = 64.sp)
        Text("ವಿರಾಸತ್ ನಮ್ಮ", color = Gold, fontSize = 38.sp, fontWeight = FontWeight.Bold)
        Text("Virasat-Namma", color = LightGold, fontSize = 24.sp)
        Text("Smart Heritage Guide · Karnataka", color = LightGold.copy(0.8f), fontSize = 13.sp)

        Spacer(modifier = Modifier.height(32.dp))

        Card(
            backgroundColor = White.copy(0.07f),
            shape = RoundedCornerShape(20.dp),
            elevation = 0.dp,
            border = BorderStroke(1.dp, Gold.copy(0.3f))
        ) {
            Column(modifier = Modifier.padding(24.dp)) {
                Text("NAME / ಹೆಸರು", color = Gold, fontSize = 11.sp)
                TextField(
                    value = name,
                    onValueChange = { name = it },
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.textFieldColors(textColor = Color.White)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text("PASSWORD / ಪಾಸ್‌ವರ್ಡ್", color = Gold, fontSize = 11.sp)
                TextField(
                    value = password,
                    onValueChange = { password = it },
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.textFieldColors(textColor = Color.White)
                )
                Spacer(modifier = Modifier.height(24.dp))
                Button(
                    onClick = { if (name.isNotBlank()) onLogin(name) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Saffron)
                ) {
                    Text("Enter the Heritage 🛕", color = DarkBrown, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Composable
fun MainContainer(
    username: String,
    selectedTab: String,
    sites: List<Site>,
    onTabSelect: (String) -> Unit,
    onSiteSelect: (Site) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("ವಿರಾಸತ್ ನಮ್ಮ", color = Gold) },
                backgroundColor = DarkBrown,
                contentColor = Gold,
                elevation = 0.dp
            )
        },
        bottomBar = {
            BottomNavigation(backgroundColor = DarkBrown) {
                listOf("home" to "🏠", "map" to "🗺️", "qr" to "📷", "passport" to "📜").forEach { (id, icon) ->
                    BottomNavigationItem(
                        selected = selectedTab == id,
                        onClick = { onTabSelect(id) },
                        icon = { Text(icon, fontSize = 20.sp) },
                        label = { Text(id.capitalize(), fontSize = 10.sp) },
                        selectedContentColor = Gold,
                        unselectedContentColor = Gold.copy(0.5f)
                    )
                }
            }
        },
        backgroundColor = Background
    ) { padding ->
        Box(modifier = Modifier.padding(padding)) {
            when (selectedTab) {
                "home" -> HomeScreen(username, sites, onSiteSelect)
                "passport" -> PassportScreen(username, sites)
                // Add Map and QR screens as needed
            }
        }
    }
}

@Composable
fun HomeScreen(username: String, sites: List<Site>, onSiteSelect: (Site) -> Unit) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        item {
            Box(modifier = Modifier.fillMaxWidth().background(Color(0xFF5C2000)).padding(24.dp)) {
                Column {
                    Text("🙏 Namaskara,", color = LightGold.copy(0.7f), fontSize = 13.sp)
                    Text(username, color = Gold, fontSize = 26.sp, fontWeight = FontWeight.Bold)
                    Text("\"ಭೂಮಿ ಮೇಲಿನ ಸ್ವರ್ಗ ಕರ್ನಾಟಕ\"", color = Cream, fontSize = 14.sp)
                }
            }
        }
        item {
            Text("🛕 Heritage Sites Nearby", 
                modifier = Modifier.padding(16.dp), 
                fontWeight = FontWeight.Bold, 
                color = DarkBrown)
        }
        items(sites) { site ->
            SiteCard(site, onSiteSelect)
        }
    }
}

@Composable
fun SiteCard(site: Site, onClick: (Site) -> Unit) {
    Card(
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp).clickable { onClick(site) },
        shape = RoundedCornerShape(16.dp),
        elevation = 4.dp
    ) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Text(site.icon, fontSize = 30.sp, modifier = Modifier.padding(end = 16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(site.name, fontWeight = FontWeight.Bold, color = DarkBrown)
                Text(site.era, fontSize = 11.sp, color = Stone)
                if (site.checkedIn) {
                    Text("✓ Visited", color = Jade, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                }
            }
            Text("📍 ${site.distance}", color = Jade, fontSize = 12.sp, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun DetailScreen(site: Site, onBack: () -> Unit, onCheckIn: (String) -> Unit) {
    Column(modifier = Modifier.fillMaxSize().background(Background).verticalScroll(rememberScrollState())) {
        Box(modifier = Modifier.fillMaxWidth().background(Color(0xFF5C2000)).padding(24.dp), contentAlignment = Alignment.Center) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                IconButton(onClick = onBack, modifier = Modifier.align(Alignment.Start)) {
                    Text("← Back", color = Gold)
                }
                Text(site.icon, fontSize = 72.sp)
                Text(site.name, color = Gold, fontSize = 24.sp, fontWeight = FontWeight.Bold)
                Text("📍 ${site.location}", color = LightGold.copy(0.7f))
            }
        }
        Column(modifier = Modifier.padding(20.dp)) {
            Text("📖 About this Site", color = Saffron, fontWeight = FontWeight.Bold, fontSize = 11.sp)
            Text(site.description, color = TextMid, lineHeight = 22.sp)
            Spacer(modifier = Modifier.height(16.dp))
            Card(backgroundColor = DarkBrown, shape = RoundedCornerShape(16.dp)) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("✨ Hidden Fact Unlocked!", color = Gold, fontWeight = FontWeight.Bold)
                    Text(site.hiddenFact, color = LightGold)
                }
            }
            Spacer(modifier = Modifier.height(24.dp))
            Button(
                onClick = { onCheckIn(site.id) },
                modifier = Modifier.fillMaxWidth().height(56.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = if (site.checkedIn) Jade else Saffron),
                shape = RoundedCornerShape(14.dp)
            ) {
                Text(if (site.checkedIn) "✓ Visited — Stamp Collected!" else "📍 Check In", color = Color.White)
            }
        }
    }
}

@Composable
fun PassportScreen(username: String, sites: List<Site>) {
    val checkedCount = sites.count { it.checkedIn }
    Column(modifier = Modifier.fillMaxSize().padding(20.dp).verticalScroll(rememberScrollState())) {
        Card(backgroundColor = Color(0xFF5C2000), shape = RoundedCornerShape(20.dp)) {
            Column(modifier = Modifier.fillMaxWidth().padding(24.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                Text("🪔 Travel Passport", color = Gold, fontWeight = FontWeight.Bold)
                Text(username, color = LightGold.copy(0.8f))
                Text("$checkedCount", fontSize = 36.sp, color = Color.White, fontWeight = FontWeight.Bold)
                Text("SITES VISITED", color = LightGold.copy(0.6f), fontSize = 12.sp)
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
        Text("🏅 Heritage Stamps", fontWeight = FontWeight.Bold, color = DarkBrown)
        Spacer(modifier = Modifier.height(10.dp))
        sites.forEach { site ->
            Card(
                modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                backgroundColor = if (site.checkedIn) Jade else Color.White
            ) {
                Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                    Text(if (site.checkedIn) site.icon else "❓", fontSize = 24.sp)
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(if (site.checkedIn) site.name else "????", color = if (site.checkedIn) Color.White else Stone)
                }
            }
        }
    }
}
