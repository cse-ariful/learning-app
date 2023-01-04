package com.online.learning.features.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.online.learning.features.utils.VoidCallback
import com.online.learning.ui.theme.ELearningTheme
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import kotlin.math.roundToInt


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarPage(navController: NavController) {
    Scaffold {
        Box(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
                .background(Color(0xffF5EDED))
        ) {
            CalendarWidget(1, 2023)
        }
    }
}

data class DayModel(val title: String, val date: Date, val isCurrentMonth: Boolean = true)
data class DateSelectionInfo(
    val date: Date? = null, val startDate: Date? = null, val endDate: Date? = null
)

data class CalendarCellConfig(
    val rangeColor: Color = Color(0xffD3E0F8),
    val startDateColor: Color = Color(0xff3871E0),
    val endDateColor: Color = Color(0xff3871E0),
    val textColor: Color = Color(0xff292929),
    val rangeTextColor: Color = Color(0xff292929),
    val selectedTextColor: Color = Color.White,
    val isMultiSelection: Boolean = true,
)

enum class DateSelectionState {
    EXACT, START, END, IN_RANGE, NONE
}

enum class CellPosition {
    START, INSIDE, END
}

val weekDays = listOf("Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat")

@Composable
fun CalendarWidget(
    month: Int,
    year: Int,
    initialDate: DateSelectionInfo = DateSelectionInfo(),
    config: CalendarCellConfig = CalendarCellConfig()
) {

    val calendar = Calendar.getInstance()

    calendar.set(Calendar.YEAR, year)
    calendar.set(Calendar.MONTH, month)

    calendar.add(Calendar.DAY_OF_MONTH, 1)

    val curDate = calendar.time
    calendar.add(Calendar.DAY_OF_MONTH, 5)
    val newDate = calendar.time
    val selection = remember {
        mutableStateOf(initialDate.copy(startDate = curDate, endDate = newDate))
    }
    val currentDate = calendar.time
    val dates = remember {
        val days = mutableListOf<DayModel>()
        calendar.set(Calendar.DAY_OF_MONTH, 1)
        val maxDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
        var dayCount = 0
        while (dayCount++ < maxDay) {
            days.add(DayModel(calendar.get(Calendar.DAY_OF_MONTH).toString(), calendar.time))
            calendar.add(Calendar.DAY_OF_MONTH, 1)
        }
        while (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY) {
            days.add(DayModel(calendar.get(Calendar.DAY_OF_MONTH).toString(), calendar.time, false))
            calendar.add(Calendar.DAY_OF_MONTH, 1)
        }
        calendar.set(Calendar.DAY_OF_MONTH, 1)
        calendar.add(Calendar.DAY_OF_MONTH, -1)
        calendar.set(Calendar.DAY_OF_MONTH, 1)
        while (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
            calendar.add(Calendar.DAY_OF_MONTH, -1)
            days.add(
                0, DayModel(calendar.get(Calendar.DAY_OF_MONTH).toString(), calendar.time, false)
            )
        }
        days
    }


    Box(
        modifier = Modifier.fillMaxWidth()
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp)
                .padding(12.dp)
                .background(Color.White, shape = RoundedCornerShape(24.dp))
                .padding(top = 24.dp, start = 12.dp, end = 12.dp, bottom = 16.dp)
        ) {

            CalendarTitle(date = currentDate, modifier = Modifier.padding(top = 16.dp))
            CalendarHeader(modifier = Modifier.padding(top = 12.dp, bottom = 8.dp))
            MainCalendarWidget(dates, selection.value, config) { item ->
                if (config.isMultiSelection) {
                    val currentSelection = selection.value
                    if (currentSelection.startDate == null) {
                        selection.value = DateSelectionInfo(startDate = item.date)
                    } else if (currentSelection.endDate == null) {
                        if (currentSelection.startDate.before(item.date)) {
                            selection.value = currentSelection.copy(endDate = item.date)
                        } else {
                            selection.value = DateSelectionInfo(
                                startDate = item.date, endDate = currentSelection.startDate
                            )
                        }
                    } else {
                        selection.value = DateSelectionInfo(startDate = item.date)
                    }
                }
            }
        }
        Row(horizontalArrangement = Arrangement.SpaceAround, modifier = Modifier.fillMaxWidth()) {
            CalendarHangerPin()
            CalendarHangerPin()
        }
    }
}

@Composable
fun MainCalendarWidget(
    dates: List<DayModel>,
    todayDate: DateSelectionInfo,
    config: CalendarCellConfig,
    onclick: (DayModel) -> Unit
) {
    LazyColumn(
        modifier = Modifier, verticalArrangement = Arrangement.spacedBy(1.dp)
    ) {
        val rowCount = (dates.size / weekDays.size.toFloat()).roundToInt()
        items(rowCount) { rowNo ->
            DateRowBuilder(dates, todayDate, rowNo, config, onclick)
        }
    }
}

@Composable
fun CalendarHangerPin() {
    Box(
        modifier = Modifier
            .padding(top = 20.dp)
            .width(20.dp)
            .height(42.dp)
    ) {
        Box(
            modifier = Modifier
                .size(18.dp)
                .align(Alignment.BottomCenter)
                .background(color = Color(0xffCCCCCC), shape = CircleShape)
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .width(12.dp)
                .height(36.dp)
                .padding(bottom = 6.dp, start = 4.dp, end = 4.dp)
                .background(Color.White, shape = RoundedCornerShape(24.dp))
        )
    }
}

@Composable
fun CalendarHeader(modifier: Modifier = Modifier) {
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly, modifier = modifier.fillMaxWidth()
    ) {
        for (item in weekDays) {
            Text(
                text = item,
                color = Color(0xFF6F6E6E),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.W500,
                fontSize = 15.sp,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

val headerDateFormat = SimpleDateFormat("MMMM yyyy")

@Composable
fun CalendarTitle(
    modifier: Modifier = Modifier,
    date: Date,
    onPrevMonth: VoidCallback? = null,
    onNextMonth: VoidCallback? = null
) {
    Column {
        Row(
            horizontalArrangement = Arrangement.SpaceAround,
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(Icons.Filled.ArrowDropDown,
                contentDescription = "",
                modifier = Modifier
                    .size(32.dp)
                    .rotate(90f)
                    .clickable(enabled = onPrevMonth != null) { onPrevMonth?.invoke() })
            Text(
                text = headerDateFormat.format(date),
                style = MaterialTheme.typography.titleMedium.copy(fontSize = 15.sp)
            )
            Icon(Icons.Filled.ArrowDropDown,
                contentDescription = "",
                modifier = Modifier
                    .size(32.dp)
                    .rotate(270f)
                    .clickable(enabled = onNextMonth != null) { onNextMonth?.invoke() })
        }
    }
}

@Composable
fun DateRowBuilder(
    dates: List<DayModel>,
    selection: DateSelectionInfo,
    rowNo: Int,
    config: CalendarCellConfig,
    onclick: (DayModel) -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.SpaceAround, modifier = Modifier.fillMaxWidth()
    ) {
        for (index in 0 until 7) {
            val date = dates[(index + rowNo * 7) % dates.size]
            val selectionState = getSelectionState(selection, date.date)
            val cellPosition = when (index) {
                0 -> CellPosition.START
                6 -> CellPosition.END
                else -> CellPosition.INSIDE
            }
            CalendarCellBuilder(date, selectionState, config, cellPosition, onclick)
        }
    }
}

@Composable
private fun getSelectionState(
    selection: DateSelectionInfo, cellDate: Date
) = when {
    selection.date != null && selection.date == cellDate -> DateSelectionState.EXACT
    selection.endDate == null && selection.startDate == cellDate -> DateSelectionState.EXACT
    selection.startDate != null || selection.endDate != null -> when {
        selection.startDate == cellDate -> DateSelectionState.START
        selection.endDate == cellDate -> DateSelectionState.END
        (selection.endDate?.after(cellDate)
            ?: false) && selection.startDate?.before(cellDate) ?: false -> DateSelectionState.IN_RANGE
        else -> DateSelectionState.NONE
    }
    else -> DateSelectionState.NONE
}


@Composable
fun RowScope.CalendarCellBuilder(
    date: DayModel,
    selectionState: DateSelectionState = DateSelectionState.NONE,
    config: CalendarCellConfig,
    cellPosition: CellPosition = CellPosition.INSIDE,
    onclick: (DayModel) -> Unit
) {
    Box(
        modifier = Modifier
            .weight(0.9f)
            .aspectRatio(1f)
    ) {
        if (config.isMultiSelection && selectionState != DateSelectionState.NONE) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        color = Color(0xffD3E0F8),
                        shape = getRangeBackgroundShape(selectionState, cellPosition)
                    )
            )
        }
        Box(modifier = Modifier
            .fillMaxSize()
            .background(
                color = getCellbackgroundColor(selectionState, config), shape = CircleShape
            )
            .clickable(enabled = date.isCurrentMonth) {
                onclick(date)
            }) {
            Text(
                text = date.title,
                fontSize = 16.sp,
                fontWeight = if (date.isCurrentMonth) FontWeight.W500 else FontWeight.W400,
                textAlign = TextAlign.Center,
                color = getDateTextColor(selectionState, config, date),
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Center)
            )
        }
    }

}

@Composable
private fun getDateTextColor(
    selectionState: DateSelectionState, config: CalendarCellConfig, date: DayModel
) = when {
    selectionState == DateSelectionState.START || selectionState == DateSelectionState.END || selectionState == DateSelectionState.EXACT -> config.selectedTextColor
    selectionState == DateSelectionState.IN_RANGE -> config.rangeTextColor
    date.isCurrentMonth -> config.textColor
    else -> Color(0xffAFAFAF)
}

private fun getCellBackgroundShape(
    selectionState: DateSelectionState, cellPosition: CellPosition
): Shape {
    return CircleShape
//    when (selectionState) {
//        DateSelectionState.EXACT,
//        DateSelectionState.START,
//        DateSelectionState.END -> CircleShape
//        DateSelectionState.IN_RANGE -> when (cellPosition) {
//            CellPosition.START -> RoundedCornerShape(
//                bottomStartPercent = 50, topStartPercent = 50
//            )
//            CellPosition.INSIDE -> RoundedCornerShape(0.dp)
//            CellPosition.END -> RoundedCornerShape(
//                bottomEndPercent = 50, topEndPercent = 50
//            )
//        }
//        DateSelectionState.NONE -> RoundedCornerShape(0.dp)
//    }
}

@Composable
private fun getCellbackgroundColor(
    selectionState: DateSelectionState, config: CalendarCellConfig
) = when (selectionState) {
    DateSelectionState.START, DateSelectionState.EXACT -> config.startDateColor
    DateSelectionState.END -> config.endDateColor
    DateSelectionState.IN_RANGE -> config.rangeColor
    else -> Color.White
}

private fun getRangeBackgroundShape(
    selectionState: DateSelectionState, cellPosition: CellPosition
) = when (selectionState) {
    DateSelectionState.EXACT -> CircleShape
    DateSelectionState.START -> RoundedCornerShape(
        bottomStartPercent = 50, topStartPercent = 50
    )
    DateSelectionState.END -> RoundedCornerShape(
        bottomEndPercent = 50, topEndPercent = 50
    )
    else -> when (cellPosition) {
        CellPosition.START -> RoundedCornerShape(
            bottomStartPercent = 50, topStartPercent = 50
        )
        CellPosition.INSIDE -> RoundedCornerShape(0.dp)
        CellPosition.END -> RoundedCornerShape(
            bottomEndPercent = 50, topEndPercent = 50
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    val navController = rememberNavController()
    ELearningTheme() {
//        BuildCourseItem(LocalConfiguration.current)
        CalendarPage(navController = navController)
    }
}
