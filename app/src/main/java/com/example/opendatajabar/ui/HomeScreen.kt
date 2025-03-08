package com.example.opendatajabar.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.opendatajabar.R
import com.example.opendatajabar.viewmodel.RegionViewModel

@Composable
fun HomeScreen(
    navController: NavHostController,
) {
    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .verticalScroll(rememberScrollState())
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Banner()
            SearchRow()
            Categories(navController)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen(navController = rememberNavController())
}

@Composable
fun Categories(navController : NavHostController) {
    Row(modifier = Modifier.padding(top = 24.dp, start = 16.dp, end = 16.dp)) {
        Text(
            text = "Categories",
            color = Color.Black,
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.weight(1f),
            style = MaterialTheme.typography.titleMedium
        )

        Text(
            text = "See All",
            color = MaterialTheme.colorScheme.primary,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(start = 8.dp),
            style = MaterialTheme.typography.titleMedium
        )
    }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp, start = 16.dp, end = 16.dp)
    ) {
        // Kolom Pendidikan
        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.cat1),
                contentDescription = "Ikon Pendidikan",
                modifier = Modifier
                    .padding(top = 8.dp, bottom = 4.dp)
                    .size(80.dp)
                    .background(
                        color = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                        RoundedCornerShape(10.dp)
                    )
                    .padding(16.dp)
            )
            Text(
                text = "Pendidikan",
                fontSize = 17.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(top = 8.dp),
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.titleSmall
            )
        }

        Spacer(modifier = Modifier.width(8.dp))

        // Kolom Kesehatan
        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Image(
                painter = painterResource(id = R.drawable.cat2),
                contentDescription = "Ikon Kesehatan",
                modifier = Modifier
                    .clickable { navController.navigate("health") }
                    .padding(top = 8.dp, bottom = 4.dp)
                    .size(80.dp)
                    .background(
                        color = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                        RoundedCornerShape(10.dp)
                    )
                    .padding(16.dp)
            )
            Text(
                text = "Kesehatan",
                fontSize = 17.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(top = 8.dp),
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.titleSmall
            )
        }

        Spacer(modifier = Modifier.width(8.dp))

        // Kolom Ekonomi
        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.cat3),
                contentDescription = "Ikon Ekonomi",
                modifier = Modifier
                    .padding(top = 8.dp, bottom = 4.dp)
                    .size(80.dp)
                    .background(
                        color = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                        RoundedCornerShape(10.dp)
                    )
                    .padding(16.dp)
            )
            Text(
                text = "Ekonomi",
                fontSize = 17.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(top = 8.dp),
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.titleSmall
            )
        }
        // Kolom Ekonomi
        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.cat4),
                contentDescription = "Ikon Ekonomi",
                modifier = Modifier
                    .padding(top = 8.dp, bottom = 4.dp)
                    .clickable { navController.navigate("list") }
                    .size(80.dp)
                    .background(
                        color = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                        RoundedCornerShape(10.dp)
                    )
                    .padding(16.dp)
            )
            Text(
                text = "Wilayah",
                fontSize = 17.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(top = 8.dp),
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.titleSmall
            )
        }
    }
}

@Composable
fun Banner() {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 54.dp, start = 16.dp, end = 8.dp)
            .height(160.dp)
            .background(colorResource(id = R.color.primary), RoundedCornerShape(10.dp))
    ) {
        val (img, text, button) = createRefs()

        Image(
            painter = painterResource(id = R.drawable.ba),
            contentDescription = "Banner Image",
            modifier = Modifier
                .constrainAs(img) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    end.linkTo(parent.end)
                }
        )

        Text(
            text = "Selamat Datang",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier
                .padding(start = 16.dp, top = 16.dp)
                .constrainAs(text) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                }
        )

        Text(
            text = "Silahkan Cari Data Anda",
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold,
            color = colorResource(id = R.color.primary),
            modifier = Modifier
                .padding(start = 16.dp, top = 16.dp)
                .constrainAs(button) {
                    top.linkTo(text.bottom)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                }
                .background(Color.White, RoundedCornerShape(10.dp))
                .padding(8.dp)
        )
    }
}

@Composable
fun SearchRow() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 38.dp, start = 16.dp, end = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        var text by rememberSaveable { mutableStateOf("") }
        TextField(
            value = text,
            onValueChange = { text = it },
            label = { Text("Search", fontStyle = FontStyle.Italic) },
            leadingIcon = {
                Image(
                    painter = painterResource(id = R.drawable.search_icon),
                    contentDescription = "Search Icon",
                    modifier = Modifier.size(23.dp)
                )
            },
            shape = RoundedCornerShape(10.dp),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                cursorColor = Color(0xFF5E5E5E),
                unfocusedLabelColor = Color(0xFF5E5E5E)
            ),
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .border(
                    width = 1.dp,
                    color = Color(0xFF521C98),
                    shape = RoundedCornerShape(8.dp)
                )
                .background(Color.White)
        )
    }
}
