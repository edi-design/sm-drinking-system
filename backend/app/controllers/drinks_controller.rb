class DrinksController < ApplicationController
  def get
    #return_arry = Array.new(4)
    #return_arry[0] = "cola"

    @a = ["cola", "mate", "wasser"]
    @b = @a.map { |e| {:name => e} }

    @return_value = @b.to_json
  end

  def get_test
    #return_arry = Array.new(4)
    #return_arry[0] = "cola"

    @a = ["cola", "mate", "wasser"]
    @b = @a.map { |e| {:name => e} }

    @return_value = @b.to_json
  end
end
