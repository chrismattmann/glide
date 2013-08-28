package glide.queryparser;

import java.util.StringTokenizer;

public class QueryParser{
	protected boolean haveProfileElementExpression=false;
	
	
	public IExpression parse(StringTokenizer qTokens,IExpression exp){
		
			String tok = null;
			IExpression exp2 = null;
		
			
			if(qTokens.hasMoreTokens()){
				tok = (String)qTokens.nextToken();
				System.err.println("Examining Token "+tok);
						
				if(tok.equalsIgnoreCase("and")){
					exp2 = new AndExpression(exp,parse(qTokens,exp));
					return exp2;
				}
				else if(tok.equalsIgnoreCase("or")){
					exp2 = new OrExpression(exp,parse(qTokens,exp));
					return exp2;
				}
				else if(tok.equalsIgnoreCase("=")){
					exp2 = new EqualsExpression(exp,parse(qTokens,exp));
					return parse(qTokens,exp2);
				}
				else {
					if(!haveProfileElementExpression){
						haveProfileElementExpression=true;
						exp2 = new ProfileElementExpression(tok,tok);
						return parse(qTokens,exp2);
					}
					else {
						haveProfileElementExpression=false;
						exp2 = new Literal(tok);
						return exp2;
					}
					
				}
			}
			else return exp;

		}
	
	public static void main(String [] args){
		String usage = "java glide.queryparser.QueryParser --queryString <String>\n";
		String qString=null;
		
		
		for(int i=0; i < args.length; i++){
			if(args[i].equals("--queryString")){
				qString = args[++i];
			}
		}
		
		if(qString==null){
			System.err.println(usage);
			System.exit(1);
		}
		
		QueryParser qp = new QueryParser();
		StringTokenizer queryTokens = new StringTokenizer(qString," ");
		IExpression iExp = qp.parse(queryTokens,null);
		System.err.println(iExp.toEvalXMLString());
		
	}

	
}