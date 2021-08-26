package com.datacvg.dimp.AAChartCoreLib.AAChartCreator;

import com.datacvg.dimp.AAChartCoreLib.AAChartEnum.AAChartAnimationType;
import com.datacvg.dimp.AAChartCoreLib.AAChartEnum.AAChartSymbolStyleType;
import com.datacvg.dimp.AAChartCoreLib.AAChartEnum.AAChartType;
import com.datacvg.dimp.AAChartCoreLib.AAOptionsModel.AAAnimation;
import com.datacvg.dimp.AAChartCoreLib.AAOptionsModel.AABar;
import com.datacvg.dimp.AAChartCoreLib.AAOptionsModel.AAChart;
import com.datacvg.dimp.AAChartCoreLib.AAOptionsModel.AAColumn;
import com.datacvg.dimp.AAChartCoreLib.AAOptionsModel.AAColumnrange;
import com.datacvg.dimp.AAChartCoreLib.AAOptionsModel.AADataLabels;
import com.datacvg.dimp.AAChartCoreLib.AAOptionsModel.AAItemStyle;
import com.datacvg.dimp.AAChartCoreLib.AAOptionsModel.AALabels;
import com.datacvg.dimp.AAChartCoreLib.AAOptionsModel.AALegend;
import com.datacvg.dimp.AAChartCoreLib.AAOptionsModel.AAMarker;
import com.datacvg.dimp.AAChartCoreLib.AAOptionsModel.AAOptions;
import com.datacvg.dimp.AAChartCoreLib.AAOptionsModel.AAPie;
import com.datacvg.dimp.AAChartCoreLib.AAOptionsModel.AAPlotOptions;
import com.datacvg.dimp.AAChartCoreLib.AAOptionsModel.AASeries;
import com.datacvg.dimp.AAChartCoreLib.AAOptionsModel.AAStyle;
import com.datacvg.dimp.AAChartCoreLib.AAOptionsModel.AASubtitle;
import com.datacvg.dimp.AAChartCoreLib.AAOptionsModel.AATitle;
import com.datacvg.dimp.AAChartCoreLib.AAOptionsModel.AATooltip;
import com.datacvg.dimp.AAChartCoreLib.AAOptionsModel.AAXAxis;
import com.datacvg.dimp.AAChartCoreLib.AAOptionsModel.AAYAxis;

public class AAOptionsConstructor
{
    public static AAOptions configureChartOptions (
            AAChartModel aaChartModel
    ) {
        AAChart aaChart = new AAChart()
                .type(aaChartModel.chartType)
                .inverted(aaChartModel.inverted)
                .backgroundColor(aaChartModel.backgroundColor)
                .pinchType(aaChartModel.zoomType)
                .panning(true)
                .polar(aaChartModel.polar)
                .margin(aaChartModel.margin)
                .scrollablePlotArea(aaChartModel.scrollablePlotArea);

        AATitle aaTitle = new AATitle()
                .text(aaChartModel.title)
                .style(aaChartModel.titleStyle);

        AASubtitle aaSubtitle = new AASubtitle()
                .text(aaChartModel.subtitle)
                .align(aaChartModel.subtitleAlign)
                .style(aaChartModel.subtitleStyle);

        AATooltip aaTooltip = new AATooltip()
                .enabled(aaChartModel.tooltipEnabled)
                .shared(true)
                .crosshairs(true)
                .valueSuffix(aaChartModel.tooltipValueSuffix);

        AAPlotOptions aaPlotOptions = new AAPlotOptions()
                .series(new AASeries()
                        .stacking(aaChartModel.stacking));

        if (!aaChartModel.animationType.equals(AAChartAnimationType.Linear)) {
            aaPlotOptions.series.animation((new AAAnimation()
                    .easing(aaChartModel.animationType)
                    .duration(aaChartModel.animationDuration)
            ));
        }

        configureAAPlotOptionsMarkerStyle(aaChartModel,aaPlotOptions);
        configureAAPlotOptionsDataLabels(aaPlotOptions,aaChartModel);

        AALegend aaLegend = new AALegend()
                .enabled(aaChartModel.legendEnabled)
                .itemStyle(new AAItemStyle()
                        .color(aaChartModel.axesTextColor))
                ;

        AAOptions aaOptions = new AAOptions()
                .chart(aaChart)
                .title(aaTitle)
                .subtitle(aaSubtitle)
                .tooltip(aaTooltip)
                .plotOptions(aaPlotOptions)
                .legend(aaLegend)
                .series(aaChartModel.series)
                .colors(aaChartModel.colorsTheme)
                .touchEventEnabled(aaChartModel.touchEventEnabled)
                ;

        configureAxisContentAndStyle(aaOptions,aaChartModel);

        return aaOptions;
    }

    private static void configureAAPlotOptionsMarkerStyle (
            AAChartModel aaChartModel,
            AAPlotOptions aaPlotOptions
    ) {
        String aaChartType = aaChartModel.chartType;
        //数据点标记相关配置，只有线性图(折线图、曲线图、折线区域填充图、曲线区域填充图、散点图、折线范围填充图、曲线范围填充图、多边形图)才有数据点标记
        if (       aaChartType.equals(AAChartType.Area)
                || aaChartType.equals(AAChartType.Areaspline)
                || aaChartType.equals(AAChartType.Line)
                || aaChartType.equals(AAChartType.Spline)
                || aaChartType.equals(AAChartType.Scatter)
                || aaChartType.equals(AAChartType.Arearange)
                || aaChartType.equals(AAChartType.Areasplinerange)
                || aaChartType.equals(AAChartType.Polygon))
        {
            AAMarker aaMarker = new AAMarker()
                    .radius(aaChartModel.markerRadius)
                    .symbol(aaChartModel.markerSymbol);
            if (aaChartModel.markerSymbolStyle.equals(AAChartSymbolStyleType.InnerBlank)) {
                aaMarker.fillColor("#ffffff")
                        .lineWidth(2f)
                        .lineColor("");
            } else if (aaChartModel.markerSymbolStyle.equals(AAChartSymbolStyleType.BorderBlank)) {
                aaMarker.lineWidth(2f)
                        .lineColor(aaChartModel.backgroundColor);
            }
            AASeries aaSeries = aaPlotOptions.series;
            aaSeries.marker(aaMarker);

        }
    }


    private static void configureAAPlotOptionsDataLabels (
            AAPlotOptions aaPlotOptions,
            AAChartModel aaChartModel
    ) {
        String aaChartType = aaChartModel.chartType;

        AADataLabels aaDataLabels = new AADataLabels()
                .enabled(aaChartModel.dataLabelsEnabled);
        if (aaChartModel.dataLabelsEnabled) {
            aaDataLabels
                    .style(aaChartModel.dataLabelsStyle);
        }

        switch (aaChartType) {
            case AAChartType.Column:
                AAColumn aaColumn = new AAColumn()
                        .borderWidth(0f)
                        .borderRadius(aaChartModel.borderRadius);
                if (aaChartModel.polar) {
                    aaColumn.pointPadding(0f)
                            .groupPadding(0.005f);
                }
                aaPlotOptions.column(aaColumn);
                break;
            case AAChartType.Bar:
                AABar aaBar = new AABar()
                        .borderWidth(0f)
                        .borderRadius(aaChartModel.borderRadius)
                        ;
                if (aaChartModel.polar) {
                    aaBar.pointPadding(0f)
                            .groupPadding(0.005f);
                }
                aaPlotOptions.bar(aaBar);
                break;
            case AAChartType.Pie:
                AAPie aaPie = new AAPie()
                        .allowPointSelect(true)
                        .cursor("pointer")
                        .showInLegend(true);
                if (aaChartModel.dataLabelsEnabled) {
                    aaDataLabels.format("<b>{point.name}</b>: {point.percentage:.1f} %");
                }
                aaPlotOptions.pie(aaPie);
                break;
            case AAChartType.Columnrange:
                AAColumnrange aaColumnrange = new AAColumnrange()
                        .borderRadius(0f)
                        .borderWidth(0f)
                        ;
                aaPlotOptions.columnrange(aaColumnrange);
                break;
        }
        aaPlotOptions.series.dataLabels(aaDataLabels);

    }

    private static void configureAxisContentAndStyle (
            AAOptions aaOptions,
            AAChartModel aaChartModel
    ) {
        String aaChartType = aaChartModel.chartType;
        //x 轴和 Y 轴的相关配置,扇形图、金字塔图和漏斗图则不需要设置 X 轴和 Y 轴的相关内容
        if  (      aaChartType.equals(AAChartType.Column)
                || aaChartType.equals(AAChartType.Bar)
                || aaChartType.equals(AAChartType.Area)
                || aaChartType.equals(AAChartType.Areaspline)
                || aaChartType.equals(AAChartType.Line)
                || aaChartType.equals(AAChartType.Spline)
                || aaChartType.equals(AAChartType.Scatter)
                || aaChartType.equals(AAChartType.Bubble)
                || aaChartType.equals(AAChartType.Columnrange)
                || aaChartType.equals(AAChartType.Arearange)
                || aaChartType.equals(AAChartType.Areasplinerange)
                || aaChartType.equals(AAChartType.Boxplot)
                || aaChartType.equals(AAChartType.Waterfall)
                || aaChartType.equals(AAChartType.Polygon)
                || aaChartType.equals(AAChartType.Gauge)
        ) {
            if (!aaChartType.equals(AAChartType.Gauge)) {
                Boolean aaXAxisLabelsEnabled = aaChartModel.xAxisLabelsEnabled;
                AALabels aaXAxisLabels = new AALabels()
                        .enabled(aaXAxisLabelsEnabled);
                if (aaXAxisLabelsEnabled) {
                    aaXAxisLabels.style(new AAStyle()
                            .color(aaChartModel.axesTextColor));
                }

                AAXAxis aaXAxis = new AAXAxis()
                        .labels(aaXAxisLabels)
                        .reversed(aaChartModel.xAxisReversed)
                        .gridLineWidth(aaChartModel.xAxisGridLineWidth)
                        .categories(aaChartModel.categories)
                        .visible(aaChartModel.xAxisVisible)
                        .tickInterval(aaChartModel.xAxisTickInterval);

                aaOptions.xAxis(aaXAxis);
            }

            Boolean aaYAxisLabelsEnabled = aaChartModel.yAxisLabelsEnabled;
            AALabels aaYAxisLabels = new AALabels()
                    .enabled(aaChartModel.yAxisLabelsEnabled);
            if (aaYAxisLabelsEnabled) {
                aaYAxisLabels.style(new AAStyle()
                        .color(aaChartModel.axesTextColor));
            }

            AAYAxis aaYAxis = new AAYAxis()
                    .labels(aaYAxisLabels)
                    .min(aaChartModel.yAxisMin)
                    .max(aaChartModel.yAxisMax)
                    .allowDecimals(aaChartModel.yAxisAllowDecimals)
                    .reversed(aaChartModel.yAxisReversed)
                    .gridLineWidth(aaChartModel.yAxisGridLineWidth)
                    .title(new AATitle()
                            .text(aaChartModel.yAxisTitle)
                            .style(new AAStyle()
                                    .color(aaChartModel.axesTextColor)))
                    .lineWidth(aaChartModel.yAxisLineWidth)
                    .visible(aaChartModel.yAxisVisible)
                    ;

            aaOptions.yAxis(aaYAxis);
        }
    }


}
