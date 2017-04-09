# federal-reserve-statement-info-extractor

Our group proposal is to write a code that will examine the text of the Federal Open Market’s
Committee post meeting communique. The website with all of the present and past communication is:
https://www.federalreserve.gov/monetarypolicy/fomccalendars.htm

FOMC statements are impactful and market moving. There is much information to extract from the
statement, such as sentiment of the federal reserve’s view on economic growth and inflation or the
choice the open markets committee made for the base interest rate. 

Feedback from the Professor:

```
Looks interesting but it's quite a mix of tasks, including sentiment analysis, information extraction, and 
some unspecified text comparison.  

The extraction is a substantial job in itself, so you probably will want to cut back on the total number of tasks.
```

The following ideas are:

- Unspecified Text Comparison: 
```
Write code to create a “what is different” from the previous statement to the current statement, similar to WSJ
```

- Information Extraction:

```
Extract information on the base interest rate and use pattern matching to find out the target fed funds 
rate from the statement from 2005 to present
```

3. Utilize naïve bayes to see if we can train a program to detect whether a statement indicates
whether the Fed is more likely to raise or lower or keep interest rates the same?
a. The work flow would be: P(Neutral or Not Neutral), Probability(Raise Rates | Not
Neutral), Probability(Lower Rates | Note Neutral)
4. Explore applying word sense disambiguation to FOMC statements and examine if WSD results
are different among statements where there are changes in the base rate or policy
5. Train a tagging and information extraction program on different interest rate regimes and check
the results when passing documents from a different interest rate regime 
