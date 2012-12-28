require 'net/http'

class SimpleAsyncServletTester

	def run
		threads = (0..100).inject([]) { |memo, i| 
			memo << Thread.new do 
				Net::HTTP.get_print URI('http://zhu13964:8088/servlet3-example-1.0-SNAPSHOT/simpleasync')
				puts
			end
		} 
		threads.each { |t| t.join }
	end

end

SimpleAsyncServletTester.new.run
